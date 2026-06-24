package com.example.UserCRUD.service;

import com.example.UserCRUD.dto.request.Create.CreateCustomerMasterRequest;
import com.example.UserCRUD.dto.request.Update.UpdateCustomerMasterRequest;
import com.example.UserCRUD.dto.response.CustomerFullDetailsResponse;
import com.example.UserCRUD.dto.response.CustomerMasterResponse;

import java.util.List;


public interface CustomerMasterService {

    CustomerMasterResponse createCustomer(CreateCustomerMasterRequest request);

    List<CustomerMasterResponse> getAllCustomers();

    CustomerMasterResponse getCustomerById(Long id);

    CustomerMasterResponse updateCustomer(Long id, UpdateCustomerMasterRequest request);

    void deleteCustomer(Long id);

    CustomerFullDetailsResponse getCustomerFullDetails(Long id);
}
