package com.example.UserCRUD.serviceimpl;

import com.example.UserCRUD.dto.request.CreateCustomerMasterRequest;
import com.example.UserCRUD.dto.request.UpdateCustomerMasterRequest;
import com.example.UserCRUD.dto.response.CustomerMasterResponse;
import com.example.UserCRUD.exception.ResourceNotFoundException;
import com.example.UserCRUD.model.CustomerMaster;
import com.example.UserCRUD.repository.CustomerMasterRepository;
import com.example.UserCRUD.service.CustomerMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerMasterServiceImpl implements CustomerMasterService {

    private final CustomerMasterRepository customerMasterRepository;

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