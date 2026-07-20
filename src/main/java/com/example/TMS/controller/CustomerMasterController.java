package com.example.TMS.controller;

import com.example.TMS.dto.request.Create.CreateCustomerMasterRequest;
import com.example.TMS.dto.request.Update.UpdateCustomerMasterRequest;
import com.example.TMS.dto.response.CustomerFullDetailsResponse;
import com.example.TMS.dto.response.CustomerMasterResponse;
import com.example.TMS.dto.response.CustomerOrderWithRouteResponse;
import com.example.TMS.service.CustomerMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerMasterController {

    private final CustomerMasterService customerService;

    // POST /api/customers — create a new customer
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<CustomerMasterResponse> createCustomer(@RequestBody CreateCustomerMasterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(request));
    }

    // GET /api/customers — get all customers
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CustomerMasterResponse>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    // GET /api/customers/{id} — get one customer
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CustomerMasterResponse> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    // PUT /api/customers/{id} — update a customer
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<CustomerMasterResponse> updateCustomer(
            @PathVariable Long id,
            @RequestBody UpdateCustomerMasterRequest request) {
        return ResponseEntity.ok(customerService.updateCustomer(id, request));
    }

    // DELETE /api/customers/{id} — delete a customer
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/full-details")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CustomerFullDetailsResponse> getCustomerFullDetails(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerFullDetails(id));
    }

    @GetMapping("/{id}/full-details-with-route")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CustomerOrderWithRouteResponse> getCustomerOrderWithRoute(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerOrderWithRouteResponse(id));
    }
}