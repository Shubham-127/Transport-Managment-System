package com.example.TMS.service;

import com.example.TMS.dto.request.Create.CreateCustomerMasterRequest;
import com.example.TMS.dto.request.Update.UpdateCustomerMasterRequest;
import com.example.TMS.dto.response.CustomerFullDetailsResponse;
import com.example.TMS.dto.response.CustomerMasterResponse;
import com.example.TMS.dto.response.CustomerOrderWithRouteResponse;

import java.util.List;


public interface CustomerMasterService {

    CustomerMasterResponse createCustomer(CreateCustomerMasterRequest request);

    List<CustomerMasterResponse> getAllCustomers();

    CustomerMasterResponse getCustomerById(Long id);

    CustomerMasterResponse updateCustomer(Long id, UpdateCustomerMasterRequest request);

    void deleteCustomer(Long id);

    CustomerFullDetailsResponse getCustomerFullDetails(Long id);
    CustomerOrderWithRouteResponse getCustomerOrderWithRouteResponse(Long id);
}
