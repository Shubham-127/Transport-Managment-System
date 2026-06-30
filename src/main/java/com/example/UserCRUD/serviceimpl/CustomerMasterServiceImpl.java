package com.example.UserCRUD.serviceimpl;

import com.example.UserCRUD.dto.request.Create.CreateCustomerMasterRequest;
import com.example.UserCRUD.dto.request.Update.UpdateCustomerMasterRequest;
import com.example.UserCRUD.dto.response.CustomerFullDetailsResponse;
import com.example.UserCRUD.dto.response.CustomerMasterResponse;
import com.example.UserCRUD.dto.response.OrderLinesMasterResponse;
import com.example.UserCRUD.dto.response.OrderWithLinesResponse;
import com.example.UserCRUD.exception.ResourceNotFoundException;
import com.example.UserCRUD.model.CustomerMaster;
import com.example.UserCRUD.model.OrderLinesMaster;
import com.example.UserCRUD.model.OrderMaster;
import com.example.UserCRUD.repository.CustomerMasterRepository;
import com.example.UserCRUD.repository.OrderLinesMasterRepository;
import com.example.UserCRUD.repository.OrderMasterRepository;
import com.example.UserCRUD.service.CustomerMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerMasterServiceImpl implements CustomerMasterService {

    private final CustomerMasterRepository customerMasterRepository;
    // NEW — needed to fetch this customer's orders
    private final OrderMasterRepository orderMasterRepository;

    // NEW — needed to fetch each order's lines
    private final OrderLinesMasterRepository orderLinesMasterRepository;

    // ... your existing createCustomer, getAllCustomers, getCustomerById,
    // updateCustomer, deleteCustomer, mapToResponseDTO methods stay exactly
    // as they already are — just add this new method below them ...

    @Override
    public CustomerFullDetailsResponse getCustomerFullDetails(Long id) {

        // Step 1 — find the customer, or throw 404
        CustomerMaster customer = customerMasterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));

        // Step 2 — find ALL orders belonging to this customer
        // Since OrderMaster has a @ManyToOne "customer" field,
        // this navigates through that relationship
        List<OrderMaster> orders = orderMasterRepository.findAll()
                .stream()
                .filter(order -> order.getCustomer().getId().equals(customer.getId()))
                .collect(Collectors.toList());

        // Step 3 — for EACH order, find its lines and build the
        // nested OrderWithLinesResponse
        List<OrderWithLinesResponse> ordersWithLines = orders.stream()
                .map(order -> {
                    List<OrderLinesMasterResponse> lineResponses =
                            orderLinesMasterRepository.findByOrderMasterId(order.getId())
                                    .stream()
                                    .map(this::mapLineToResponseDTO)
                                    .collect(Collectors.toList());

                    return mapOrderToResponseDTO(order, lineResponses);
                })
                .collect(Collectors.toList());

        // Step 4 — build the full customer response, nesting
        // the orders (which already nest their lines) inside it
        return CustomerFullDetailsResponse.builder()
                .id(customer.getId())
                .customerId(customer.getCustomerId())
                .customerName(customer.getCustomerName())
                .getNumber(customer.getGetNumber())
                .panNumber(customer.getPanNumber())
                .contactPerson(customer.getContactPerson())
                .mobile(customer.getMobile())
                .email(customer.getEmail())
                .address(customer.getAddress())
                .city(customer.getCity())
                .state(customer.getState())
                .pincode(customer.getPincode())
                .country(customer.getCountry())
                .active(customer.getActive())
                .remarks(customer.getRemarks())
                .salesOrder(customer.getSalesOrder())
                .orders(ordersWithLines)
                .build();
    }

    // ─── NEW PRIVATE HELPERS ──────────────────────────────────

    // Converts an OrderMaster entity + its already-built line list
    // into the nested OrderWithLinesResponse
    private OrderWithLinesResponse mapOrderToResponseDTO(OrderMaster order, List<OrderLinesMasterResponse> lines) {
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
                .lines(lines)
                .build();
    }

    // Converts an OrderLinesMaster entity into its response DTO —
    // same shape as your existing OrderLinesMasterServiceImpl mapper
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


    @Override
    public CustomerMasterResponse createCustomer(CreateCustomerMasterRequest request) {

        if (customerMasterRepository.existsByCustomerId(request.getCustomerId())) {
            throw new RuntimeException("Customer already exists with customerId: " + request.getCustomerId());
        }

        if (customerMasterRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Customer already exists with email: " + request.getEmail());
        }

        CustomerMaster customer = CustomerMaster.builder()
                .customerId(request.getCustomerId())
                .customerName(request.getCustomerName())
                .getNumber(request.getGetNumber())
                .panNumber(request.getPanNumber())
                .contactPerson(request.getContactPerson())
                .mobile(request.getMobile())
                .email(request.getEmail())
                .address(request.getAddress())
                .city(request.getCity())
                .state(request.getState())
                .pincode(request.getPincode())
                .country(request.getCountry())
                .active(request.getActive())
                .remarks(request.getRemarks())
                .salesOrder(request.getSalesOrder())
                .build();

        CustomerMaster saved = customerMasterRepository.save(customer);
        return mapToResponseDTO(saved);
    }

    @Override
    public List<CustomerMasterResponse> getAllCustomers() {
        return customerMasterRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerMasterResponse getCustomerById(Long id) {
        CustomerMaster customer = customerMasterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        return mapToResponseDTO(customer);
    }

    @Override
    public CustomerMasterResponse updateCustomer(Long id, UpdateCustomerMasterRequest request) {

        CustomerMaster existing = customerMasterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));

        CustomerMaster updatedCustomer = CustomerMaster.builder()
                .id(existing.getId())
                .customerId(request.getCustomerId())
                .customerName(request.getCustomerName())
                .getNumber(request.getGetNumber())
                .panNumber(request.getPanNumber())
                .contactPerson(request.getContactPerson())
                .mobile(request.getMobile())
                .email(request.getEmail())
                .address(request.getAddress())
                .city(request.getCity())
                .state(request.getState())
                .pincode(request.getPincode())
                .country(request.getCountry())
                .active(request.getActive())
                .remarks(request.getRemarks())
                .salesOrder(request.getSalesOrder())
                .build();

        CustomerMaster saved = customerMasterRepository.save(updatedCustomer);
        return mapToResponseDTO(saved);
    }

    @Override
    public void deleteCustomer(Long id) {
        CustomerMaster customer = customerMasterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        customerMasterRepository.delete(customer);
    }

    private CustomerMasterResponse mapToResponseDTO(CustomerMaster customer) {
        return CustomerMasterResponse.builder()
                .id(customer.getId())
                .customerId(customer.getCustomerId())
                .customerName(customer.getCustomerName())
                .getNumber(customer.getGetNumber())
                .panNumber(customer.getPanNumber())
                .contactPerson(customer.getContactPerson())
                .mobile(customer.getMobile())
                .email(customer.getEmail())
                .address(customer.getAddress())
                .city(customer.getCity())
                .state(customer.getState())
                .pincode(customer.getPincode())
                .country(customer.getCountry())
                .active(customer.getActive())
                .remarks(customer.getRemarks())
                .salesOrder(customer.getSalesOrder())
                .build();
    }
}