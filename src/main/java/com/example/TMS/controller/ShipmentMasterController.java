package com.example.TMS.controller;

import com.example.TMS.dto.request.Create.CreateShipmentMasterRequest;
import com.example.TMS.dto.response.ShipmentMasterResponse;
import com.example.TMS.service.ShipmentMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
@RequiredArgsConstructor
public class ShipmentMasterController {

    private final ShipmentMasterService shipmentMasterService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<ShipmentMasterResponse> createShipment(@RequestBody CreateShipmentMasterRequest request) {
        return ResponseEntity.ok(shipmentMasterService.createShipment(request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ShipmentMasterResponse> getShipmentById(@PathVariable Long id) {
        return ResponseEntity.ok(shipmentMasterService.getShipmentById(id));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ShipmentMasterResponse>> getAllShipments() {
        return ResponseEntity.ok(shipmentMasterService.getAllShipments());
    }

    @GetMapping("/customer/{customerId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ShipmentMasterResponse>> getShipmentsByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(shipmentMasterService.getShipmentsByCustomer(customerId));
    }
    @PostMapping("/{shipmentId}/orders")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<ShipmentMasterResponse> addOrdersToShipment(
            @PathVariable Long shipmentId,
            @RequestBody CreateShipmentMasterRequest request) {
        return ResponseEntity.ok(shipmentMasterService.addOrdersToShipment(shipmentId, request));
    }
}