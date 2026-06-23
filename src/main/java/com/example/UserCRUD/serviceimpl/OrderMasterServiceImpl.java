package com.example.UserCRUD.serviceimpl;

import com.example.UserCRUD.dto.request.Create.CreateOrderMasterRequest;
import com.example.UserCRUD.dto.request.Update.UpdateOrderMasterRequest;
import com.example.UserCRUD.dto.response.OrderMasterResponse;
import com.example.UserCRUD.exception.ResourceNotFoundException;
import com.example.UserCRUD.model.CustomerMaster;
import com.example.UserCRUD.model.OrderMaster;
import com.example.UserCRUD.repository.CustomerMasterRepository;
import com.example.UserCRUD.repository.OrderMasterRepository;
import com.example.UserCRUD.service.OrderMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderMasterServiceImpl implements OrderMasterService {

    private final OrderMasterRepository orderMasterRepository;
    private final CustomerMasterRepository customerMasterRepository;

    @Override
    public OrderMasterResponse createOrder(CreateOrderMasterRequest request) {

        if (orderMasterRepository.existsByOrderNumber(request.getOrderNumber())) {
            throw new RuntimeException("Order already exists with orderNumber: " + request.getOrderNumber());
        }

        Long customerIdAsLong = Long.parseLong(request.getCustomerNumber());

        CustomerMaster customer = customerMasterRepository.findByCustomerId(customerIdAsLong)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with customerNumber:" + request.getCustomerNumber()));

        OrderMaster order = OrderMaster.builder()
                .orderNumber(Long.valueOf(request.getOrderNumber()))
                .orderType(request.getOrderType())
                .company(request.getCompany())
                .customer(customer)
                .orderDate(request.getOrderDate())
                .requestedDate(request.getRequestedDate())
                .branchPlant(request.getBranchPlant())
                .shipToCustomerNumber(Long.valueOf(request.getShipToCustomerNumber()))
                .shipToCustomerName(request.getShipToCustomerName())
                .shipToAddress1(request.getShipToAddress1())
                .shipToCity(request.getShipToCity())
                .shipToState(request.getShipToState())
                .shipToPinCode(request.getShipToPinCode())
                .unitWeight(request.getUnitWeight())
                .weightUnit(request.getWeightUnit())
                .unitVolume(request.getUnitVolume())
                .volumeUnit(request.getVolumeUnit())
                .status(request.getStatus())
                .currencyCode(request.getCurrencyCode())
                .build();

        OrderMaster saved = orderMasterRepository.save(order);
        return mapToResponseDTO(saved);
    }

    @Override
    public List<OrderMasterResponse> getAllOrders() {
        return orderMasterRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderMasterResponse getOrderById(Long id) {
        OrderMaster order = orderMasterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        return mapToResponseDTO(order);
    }

    @Override
    public OrderMasterResponse updateOrder(Long id, UpdateOrderMasterRequest request) {
        OrderMaster existing = orderMasterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        Long customerIdAsLong = Long.parseLong(request.getCustomerNumber());
        CustomerMaster customer = customerMasterRepository.findByCustomerId(customerIdAsLong)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found with customerNumber: " + request.getCustomerNumber()));

        OrderMaster updatedOrder = OrderMaster.builder()
                .id(existing.getId())
                .orderNumber(Long.valueOf(request.getOrderNumber()))
                .orderType(request.getOrderType())
                .company(request.getCompany())
                .customer(customer)
                .orderDate(request.getOrderDate())
                .requestedDate(request.getRequestedDate())
                .branchPlant(request.getBranchPlant())
                .shipToCustomerNumber(Long.valueOf(request.getShipToCustomerNumber()))
                .shipToCustomerName(request.getShipToCustomerName())
                .shipToAddress1(request.getShipToAddress1())
                .shipToCity(request.getShipToCity())
                .shipToState(request.getShipToState())
                .shipToPinCode(request.getShipToPinCode())
                .unitWeight(request.getUnitWeight())
                .weightUnit(request.getWeightUnit())
                .unitVolume(request.getUnitVolume())
                .volumeUnit(request.getVolumeUnit())
                .status(request.getStatus())
                .currencyCode(request.getCurrencyCode())
                .build();

        OrderMaster saved = orderMasterRepository.save(updatedOrder);
        return mapToResponseDTO(saved);
    }

    @Override
    public void deleteOrder(Long id) {
        OrderMaster order = orderMasterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        orderMasterRepository.delete(order);
    }

    private OrderMasterResponse mapToResponseDTO(OrderMaster order) {
        return OrderMasterResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .orderType(order.getOrderType())
                .company(order.getCompany())
                .customerNumber(String.valueOf(order.getCustomer().getCustomerId()))

                .orderDate(order.getOrderDate())
                .requestedDate(order.getRequestedDate())
                .branchPlant(order.getBranchPlant())
                .shipToCustomerNumber(order.getShipToCustomerNumber())
                .shipToCustomerName(order.getShipToCustomerName())
                .shipToAddress1(order.getShipToAddress1())
                .shipToCity(order.getShipToCity())
                .shipToState(order.getShipToState())
                .shipToPinCode(order.getShipToPinCode())
                .unitWeight(order.getUnitWeight())
                .weightUnit(order.getWeightUnit())
                .unitVolume(order.getUnitVolume())
                .volumeUnit(order.getVolumeUnit())
                .status(order.getStatus())
                .currencyCode(order.getCurrencyCode())
                .build();
    }
}
