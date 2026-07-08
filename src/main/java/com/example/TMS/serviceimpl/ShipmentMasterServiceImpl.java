package com.example.TMS.serviceimpl;

import com.example.TMS.dto.request.Create.CreateShipmentMasterRequest;
import com.example.TMS.dto.response.OrderLinesMasterResponse;
import com.example.TMS.dto.response.OrderWithLinesResponse;
import com.example.TMS.dto.response.ShipmentMasterResponse;
import com.example.TMS.exception.ResourceNotFoundException;
import com.example.TMS.model.CustomerMaster;
import com.example.TMS.model.OrderLinesMaster;
import com.example.TMS.model.OrderMaster;
import com.example.TMS.model.RouteMaster;
import com.example.TMS.model.ShipmentMaster;
import com.example.TMS.model.StopMaster;
import com.example.TMS.repository.CustomerMasterRepository;
import com.example.TMS.repository.OrderLinesMasterRepository;
import com.example.TMS.repository.OrderMasterRepository;
import com.example.TMS.repository.RouteMasterRepository;
import com.example.TMS.repository.ShipmentMasterRepository;
import com.example.TMS.repository.StopMasterRepository;
import com.example.TMS.service.ShipmentMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShipmentMasterServiceImpl implements ShipmentMasterService {

    private final ShipmentMasterRepository shipmentMasterRepository;
    private final CustomerMasterRepository customerMasterRepository;
    private final OrderMasterRepository orderMasterRepository;
    private final OrderLinesMasterRepository orderLinesMasterRepository;
    private final RouteMasterRepository routeMasterRepository;
    private final StopMasterRepository stopMasterRepository;

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

        // Step 6.5 — resolve each order's route (via shipToCity), and make sure
        // they ALL resolve to the SAME route
        Map<String, Long> cityToRouteId = buildCityToRouteIdMap();

        List<Long> resolvedRouteIds = orders.stream()
                .map(order -> resolveRouteIdForOrder(order, cityToRouteId))
                .distinct()
                .collect(Collectors.toList());

        if (resolvedRouteIds.size() > 1) {
            throw new RuntimeException("All orders in a shipment must belong to the same route. Found multiple routes: " + resolvedRouteIds);
        }

        Long shipmentRouteId = resolvedRouteIds.get(0);

        // Step 7 — create the shipment, including order IDs + resolved route ID
        ShipmentMaster shipment = ShipmentMaster.builder()
                .shipmentNumber(request.getShipmentNumber())
                .customer(customer)
                .shipmentDate(request.getShipmentDate())
                .status(request.getStatus())
                .remarks(request.getRemarks())
                .orderIds(orders.stream().map(OrderMaster::getId).collect(Collectors.toList()))
                .routeId(shipmentRouteId)
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

        // Step 4.5 — new orders must resolve to the SAME route already set on this shipment
        Map<String, Long> cityToRouteId = buildCityToRouteIdMap();

        for (OrderMaster order : ordersToAdd) {
            Long resolvedRouteId = resolveRouteIdForOrder(order, cityToRouteId);
            if (!resolvedRouteId.equals(shipment.getRouteId())) {
                throw new RuntimeException(
                        "Order " + order.getId() + " belongs to a different route (resolved routeId: " + resolvedRouteId
                                + ") than the shipment's route (routeId: " + shipment.getRouteId() + ")");
            }
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

    // ─── ROUTE RESOLUTION HELPERS ──────────────────────────────────

    // Builds a normalized city -> routeId lookup ONCE, instead of scanning
    // all routes/stops separately for every single order
    private Map<String, Long> buildCityToRouteIdMap() {
        List<RouteMaster> allRoutes = routeMasterRepository.findAll();
        List<StopMaster> allStops = stopMasterRepository.findAll();

        Map<Long, List<StopMaster>> stopsByRouteId = allStops.stream()
                .collect(Collectors.groupingBy(stop -> stop.getRouteMaster().getId())); // adjust getter if your field is named differently

        Map<String, Long> cityToRouteId = new HashMap<>();
        for (RouteMaster route : allRoutes) {
            cityToRouteId.putIfAbsent(normalize(route.getDestinationLocation()), route.getId());
            for (StopMaster stop : stopsByRouteId.getOrDefault(route.getId(), List.of())) {
                cityToRouteId.putIfAbsent(normalize(stop.getStopLocation()), route.getId());
            }
        }
        return cityToRouteId;
    }

    // Resolves the routeId for a single order, rejecting it if no route matches its shipToCity
    private Long resolveRouteIdForOrder(OrderMaster order, Map<String, Long> cityToRouteId) {
        Long routeId = cityToRouteId.get(normalize(order.getShipToCity()));
        if (routeId == null) {
            throw new RuntimeException(
                    "Order " + order.getId() + " (shipToCity: " + order.getShipToCity() + ") does not match any route");
        }
        return routeId;
    }

    // ─── MAPPING HELPERS ──────────────────────────────────

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
                .routeId(shipment.getRouteId())
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

    private String normalize(String city) {
        return city == null ? "" : city.trim().toLowerCase();
    }
}