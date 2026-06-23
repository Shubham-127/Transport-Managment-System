package com.example.UserCRUD.controller;


import com.example.UserCRUD.dto.request.Create.CreateTransportMasterRequest;
import com.example.UserCRUD.dto.request.Update.UpdateTransportMasterRequest;
import com.example.UserCRUD.dto.response.TransportMasterResponse;
import com.example.UserCRUD.service.TransportMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transport-master")
@RequiredArgsConstructor
public class TransportMasterController {

    private final TransportMasterService transportMasterService;

    @PostMapping
    public ResponseEntity<TransportMasterResponse> createTransport(@RequestBody CreateTransportMasterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transportMasterService.createTransport(request));
    }

    @GetMapping
    public ResponseEntity<List<TransportMasterResponse>> getAllTransports() {
        return ResponseEntity.ok(transportMasterService.getAllTransports());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransportMasterResponse> getTransportById(@PathVariable Long id) {
        return ResponseEntity.ok(transportMasterService.getTransportById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransportMasterResponse> updateTransport(
            @PathVariable Long id,
            @RequestBody UpdateTransportMasterRequest request) {
        return ResponseEntity.ok(transportMasterService.updateTransport(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransport(@PathVariable Long id) {
        transportMasterService.deleteTransport(id);
        return ResponseEntity.noContent().build();
    }
}