package com.example.UserCRUD.controller;

import com.example.UserCRUD.dto.request.Create.CreateShipmentMasterRequest;
import com.example.UserCRUD.dto.request.Create.CreateShipmentMasterRequest;
import com.example.UserCRUD.dto.response.ShipmentMasterResponse;
import com.example.UserCRUD.service.ShipmentMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
@RequiredArgsConstructor
public class ShipmentMasterController {

    private final ShipmentMasterService shipmentMasterService;

    @PostMapping
    public ResponseEntity<ShipmentMasterResponse> createShipment(@RequestBody CreateShipmentMasterRequest request) {
        return ResponseEntity.ok(shipmentMasterService.createShipment(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShipmentMasterResponse> getShipmentById(@PathVariable Long id) {
        return ResponseEntity.ok(shipmentMasterService.getShipmentById(id));
    }

    @GetMapping
    public ResponseEntity<List<ShipmentMasterResponse>> getAllShipments() {
        return ResponseEntity.ok(shipmentMasterService.getAllShipments());
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ShipmentMasterResponse>> getShipmentsByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(shipmentMasterService.getShipmentsByCustomer(customerId));
    }
    @PostMapping("/{shipmentId}/orders")
    public ResponseEntity<ShipmentMasterResponse> addOrdersToShipment(
            @PathVariable Long shipmentId,
            @RequestBody CreateShipmentMasterRequest request) {
        return ResponseEntity.ok(shipmentMasterService.addOrdersToShipment(shipmentId, request));
    }
}