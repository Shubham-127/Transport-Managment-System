package com.example.UserCRUD.serviceimpl;

import com.example.UserCRUD.dto.request.Create.CreateShipmentMasterRequest;
import com.example.UserCRUD.dto.response.OrderLinesMasterResponse;
import com.example.UserCRUD.dto.response.OrderWithLinesResponse;
import com.example.UserCRUD.dto.response.ShipmentMasterResponse;
import com.example.UserCRUD.exception.ResourceNotFoundException;
import com.example.UserCRUD.model.CustomerMaster;
import com.example.UserCRUD.model.OrderLinesMaster;
import com.example.UserCRUD.model.OrderMaster;
import com.example.UserCRUD.model.ShipmentMaster;
import com.example.UserCRUD.repository.CustomerMasterRepository;
import com.example.UserCRUD.repository.OrderLinesMasterRepository;
import com.example.UserCRUD.repository.OrderMasterRepository;
import com.example.UserCRUD.repository.ShipmentMasterRepository;
import com.example.UserCRUD.service.ShipmentMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShipmentMasterServiceImpl implements ShipmentMasterService {

    private final ShipmentMasterRepository shipmentMasterRepository;
    private final CustomerMasterRepository customerMasterRepository;
    private final OrderMasterRepository orderMasterRepository;
    private final OrderLinesMasterRepository orderLinesMasterRepository;

    @Override
    @Transactional
    public ShipmentMasterResponse createShipment(CreateShipmentMasterRequest request) {

        // Step 1 — shipment number must be unique
        if (shipmentMasterRepository.existsByShipmentNumber(request.getShipmentNumber())) {
            throw new RuntimeException("Shipment already exists with shipmentNumber: " + request.getShipmentNumber());
        }

        // Step 2 — customer must exist
        CustomerMaster customer = customerMasterRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + request.getCustomerId()));

        if (request.getOrderIds() == null || request.getOrderIds().isEmpty()) {
            throw new RuntimeException("A shipment must contain at least one order");
        }

        // Step 3 — fetch all requested orders
        List<OrderMaster> orders = orderMasterRepository.findAllById(request.getOrderIds());

        // Step 4 — make sure every requested orderId was actually found
        if (orders.size() != request.getOrderIds().size()) {
            throw new ResourceNotFoundException("One or more order IDs do not exist");
        }

        // Step 5 — every order must belong to the SAME customer requested for this shipment
        boolean allSameCustomer = orders.stream()
                .allMatch(order -> order.getCustomer().getId().equals(customer.getId()));

        if (!allSameCustomer) {
            throw new RuntimeException(
                    "All orders in a shipment must belong to the same customer (customerId: " + customer.getId() + ")");
        }

        // Step 6 — an order already assigned to another shipment can't be reused
        boolean anyAlreadyShipped = orders.stream()
                .anyMatch(order -> order.getShipment() != null);

        if (anyAlreadyShipped) {
            throw new RuntimeException("One or more orders are already assigned to a different shipment");
        }

        // Step 7 — create the shipment, including the order IDs directly on the row
        ShipmentMaster shipment = ShipmentMaster.builder()
                .shipmentNumber(request.getShipmentNumber())
                .customer(customer)
                .shipmentDate(request.getShipmentDate())
                .status(request.getStatus())
                .remarks(request.getRemarks())
                .orderIds(orders.stream().map(OrderMaster::getId).collect(Collectors.toList()))
                .build();

        ShipmentMaster savedShipment = shipmentMasterRepository.save(shipment);

        // Step 8 — link each order to this shipment (order_master.shipment_id)
        orders.forEach(order -> order.setShipment(savedShipment));
        orderMasterRepository.saveAll(orders);

        return mapToResponseDTO(savedShipment, orders);
    }

    @Override
    @Transactional
    public ShipmentMasterResponse addOrdersToShipment(Long shipmentId, CreateShipmentMasterRequest request) {

        // Step 1 — shipment must exist
        ShipmentMaster shipment = shipmentMasterRepository.findById(shipmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found with id: " + shipmentId));

        if (request.getOrderIds() == null || request.getOrderIds().isEmpty()) {
            throw new RuntimeException("Provide at least one orderId to add");
        }

        // Step 2 — fetch the requested orders
        List<OrderMaster> ordersToAdd = orderMasterRepository.findAllById(request.getOrderIds());

        if (ordersToAdd.size() != request.getOrderIds().size()) {
            throw new ResourceNotFoundException("One or more order IDs do not exist");
        }

        // Step 3 — every order must belong to the SAME customer as the shipment
        // (the shipment's existing customer is the source of truth here)
        boolean allSameCustomer = ordersToAdd.stream()
                .allMatch(order -> order.getCustomer().getId().equals(shipment.getCustomer().getId()));

        if (!allSameCustomer) {
            throw new RuntimeException(
                    "All orders must belong to the shipment's customer (customerId: " + shipment.getCustomer().getId() + ")");
        }

        // Step 4 — an order already in ANY shipment can't be re-added
        boolean anyAlreadyShipped = ordersToAdd.stream()
                .anyMatch(order -> order.getShipment() != null);

        if (anyAlreadyShipped) {
            throw new RuntimeException("One or more orders are already assigned to a shipment");
        }

        // Step 5 — link them (order_master.shipment_id)
        ordersToAdd.forEach(order -> order.setShipment(shipment));
        orderMasterRepository.saveAll(ordersToAdd);

        // Step 6 — update the shipment's orderIds array to include the newly added orders
        List<Long> updatedOrderIds = new ArrayList<>(
                shipment.getOrderIds() != null ? shipment.getOrderIds() : new ArrayList<>()
        );
        ordersToAdd.forEach(order -> updatedOrderIds.add(order.getId()));
        shipment.setOrderIds(updatedOrderIds);
        ShipmentMaster savedShipment = shipmentMasterRepository.save(shipment);

        // Step 7 — return the shipment with its FULL updated order list (old + new)
        List<OrderMaster> allShipmentOrders = orderMasterRepository.findAll()
                .stream()
                .filter(order -> order.getShipment() != null && order.getShipment().getId().equals(savedShipment.getId()))
                .collect(Collectors.toList());

        return mapToResponseDTO(savedShipment, allShipmentOrders);
    }

    @Override
    public ShipmentMasterResponse getShipmentById(Long id) {
        ShipmentMaster shipment = shipmentMasterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found with id: " + id));

        List<OrderMaster> orders = orderMasterRepository.findAll()
                .stream()
                .filter(order -> order.getShipment() != null && order.getShipment().getId().equals(shipment.getId()))
                .collect(Collectors.toList());

        return mapToResponseDTO(shipment, orders);
    }

    @Override
    public List<ShipmentMasterResponse> getAllShipments() {
        return shipmentMasterRepository.findAll()
                .stream()
                .map(shipment -> {
                    List<OrderMaster> orders = orderMasterRepository.findAll()
                            .stream()
                            .filter(order -> order.getShipment() != null && order.getShipment().getId().equals(shipment.getId()))
                            .collect(Collectors.toList());
                    return mapToResponseDTO(shipment, orders);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ShipmentMasterResponse> getShipmentsByCustomer(Long customerId) {
        return shipmentMasterRepository.findByCustomerId(customerId)
                .stream()
                .map(shipment -> {
                    List<OrderMaster> orders = orderMasterRepository.findAll()
                            .stream()
                            .filter(order -> order.getShipment() != null && order.getShipment().getId().equals(shipment.getId()))
                            .collect(Collectors.toList());
                    return mapToResponseDTO(shipment, orders);
                })
                .collect(Collectors.toList());
    }

    // ─── PRIVATE HELPERS ──────────────────────────────────

    private ShipmentMasterResponse mapToResponseDTO(ShipmentMaster shipment, List<OrderMaster> orders) {

        List<OrderWithLinesResponse> orderResponses = orders.stream()
                .map(order -> {
                    List<OrderLinesMasterResponse> lineResponses =
                            orderLinesMasterRepository.findByOrderMasterId(order.getId())
                                    .stream()
                                    .map(this::mapLineToResponseDTO)
                                    .collect(Collectors.toList());

                    return OrderWithLinesResponse.builder()
                            .id(order.getId())
                            .orderNumber(String.valueOf(order.getOrderNumber()))
                            .orderType(order.getOrderType())
                            .company(order.getCompany())
                            .customerNumber(String.valueOf(order.getCustomer().getCustomerId()))
                            .orderDate(order.getOrderDate())
                            .requestedDate(order.getRequestedDate())
                            .branchPlant(order.getBranchPlant())
                            .shipToCustomerNumber(String.valueOf(order.getShipToCustomerNumber()))
                            .shipToCustomerName(order.getShipToCustomerName())
                            .shipToAddress1(order.getShipToAddress1())
                            .shipToCity(order.getShipToCity())
                            .shipToState(order.getShipToState())
                            .shipToPinCode(order.getShipToPinCode())
                            .unitWeight(order.getUnitWeight())
                            .weightUnit(order.getWeightUnit())
                            .totalVolume(order.getTotalVolume())
                            .volumeUnit(order.getVolumeUnit())
                            .status(order.getStatus())
                            .currencyCode(order.getCurrencyCode())
                            .lines(lineResponses)
                            .build();
                })
                .collect(Collectors.toList());

        return ShipmentMasterResponse.builder()
                .id(shipment.getId())
                .shipmentNumber(shipment.getShipmentNumber())
                .customerId(shipment.getCustomer().getId())
                .customerName(shipment.getCustomer().getCustomerName())
                .shipmentDate(shipment.getShipmentDate())
                .status(shipment.getStatus())
                .remarks(shipment.getRemarks())
                .orderIds(shipment.getOrderIds())
                .orders(orderResponses)
                .build();
    }

    private OrderLinesMasterResponse mapLineToResponseDTO(OrderLinesMaster line) {
        return OrderLinesMasterResponse.builder()
                .id(line.getId())
                .orderMasterId(line.getOrderMaster().getId())
                .lineNumber(line.getLineNumber())
                .itemNumber(line.getItemNumber())
                .description(line.getDescription())
                .quantity(line.getQuantity())
                .uom(line.getUom())
                .unitPrice(line.getUnitPrice())
                .lineAmount(line.getLineAmount())
                .unitWeight(line.getUnitWeight())
                .weightUnit(line.getWeightUnit())
                .unitVolume(line.getUnitVolume())
                .volumeUnit(line.getVolumeUnit())
                .build();
    }
}