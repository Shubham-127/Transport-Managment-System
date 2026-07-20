package com.example.TMS.controller;


import com.example.TMS.dto.request.Create.CreateTransportMasterRequest;
import com.example.TMS.dto.request.Update.UpdateTransportMasterRequest;
import com.example.TMS.dto.response.TransportMasterResponse;
import com.example.TMS.service.TransportMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transport-master")
@RequiredArgsConstructor
public class TransportMasterController {

    private final TransportMasterService transportMasterService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<TransportMasterResponse> createTransport(@RequestBody CreateTransportMasterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transportMasterService.createTransport(request));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<TransportMasterResponse>> getAllTransports() {
        return ResponseEntity.ok(transportMasterService.getAllTransports());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TransportMasterResponse> getTransportById(@PathVariable Long id) {
        return ResponseEntity.ok(transportMasterService.getTransportById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<TransportMasterResponse> updateTransport(
            @PathVariable Long id,
            @RequestBody UpdateTransportMasterRequest request) {
        return ResponseEntity.ok(transportMasterService.updateTransport(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTransport(@PathVariable Long id) {
        transportMasterService.deleteTransport(id);
        return ResponseEntity.noContent().build();
    }
}