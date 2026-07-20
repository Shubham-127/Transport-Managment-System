package com.example.TMS.controller;


import com.example.TMS.dto.request.Create.CreateVehicleMasterRequest;
import com.example.TMS.dto.request.Update.UpdateVehicleMasterRequest;
import com.example.TMS.dto.response.VehicleMasterResponse;
import com.example.TMS.service.VehicleMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleMasterController {

    private final VehicleMasterService vehicleMasterService;

    @PostMapping("/transport/{transportId}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<VehicleMasterResponse> createVehicle(
            @PathVariable Long transportId,
            @RequestBody CreateVehicleMasterRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(vehicleMasterService.createVehicle(
                        transportId,
                        request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<VehicleMasterResponse> getVehicleById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                vehicleMasterService.getVehicleById(id));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<VehicleMasterResponse>> getAllVehicles() {

        return ResponseEntity.ok(
                vehicleMasterService.getAllVehicles());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<VehicleMasterResponse> updateVehicle(
            @PathVariable Long id,
            @RequestBody UpdateVehicleMasterRequest request) {

        return ResponseEntity.ok(
                vehicleMasterService.updateVehicle(
                        id,
                        request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteVehicle(
            @PathVariable Long id) {

        vehicleMasterService.deleteVehicle(id);

        return ResponseEntity.noContent().build();
    }
}
