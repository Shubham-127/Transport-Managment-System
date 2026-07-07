package com.example.UserCRUD.controller;

import com.example.UserCRUD.dto.request.Create.CreateCustomerMasterRequest;
import com.example.UserCRUD.dto.request.Update.UpdateCustomerMasterRequest;
import com.example.UserCRUD.dto.response.CustomerFullDetailsResponse;
import com.example.UserCRUD.dto.response.CustomerMasterResponse;
import com.example.UserCRUD.dto.response.CustomerOrderWithRouteResponse;
import com.example.UserCRUD.service.CustomerMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerMasterController {

    private final CustomerMasterService customerService;

    // POST /api/customers — create a new customer
    @PostMapping
    public ResponseEntity<CustomerMasterResponse> createCustomer(@RequestBody CreateCustomerMasterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(request));
    }

    // GET /api/customers — get all customers
    @GetMapping
    public ResponseEntity<List<CustomerMasterResponse>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    // GET /api/customers/{id} — get one customer
    @GetMapping("/{id}")
    public ResponseEntity<CustomerMasterResponse> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    // PUT /api/customers/{id} — update a customer
    @PutMapping("/{id}")
    public ResponseEntity<CustomerMasterResponse> updateCustomer(
            @PathVariable Long id,
            @RequestBody UpdateCustomerMasterRequest request) {
        return ResponseEntity.ok(customerService.updateCustomer(id, request));
    }

    // DELETE /api/customers/{id} — delete a customer
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/full-details")
    public ResponseEntity<CustomerFullDetailsResponse> getCustomerFullDetails(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerFullDetails(id));
    }

    @GetMapping("/{id}/full-details-with-route")
    public ResponseEntity<CustomerOrderWithRouteResponse> getCustomerOrderWithRoute(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerOrderWithRouteResponse(id));
    }
}