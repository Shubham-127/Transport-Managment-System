package com.example.TMS.controller;

import com.example.TMS.dto.request.Create.CreateStopMasterRequest;
import com.example.TMS.dto.request.Update.UpdateStopMasterRequest;
import com.example.TMS.dto.response.StopMasterResponse;
import com.example.TMS.service.StopMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stops")
@RequiredArgsConstructor
public class StopMasterController {

    private final StopMasterService stopMasterService;

    // POST /api/stops/route/{routeId} — add a stop to a specific route
    // matches the pattern from your VehicleMasterController:
    // /api/vehicles/transport/{transportId}
    @PostMapping("/route/{routeId}")
    public ResponseEntity<StopMasterResponse> createStop(
            @PathVariable Long routeId,
            @RequestBody CreateStopMasterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(stopMasterService.createStop(routeId, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StopMasterResponse> getStopById(@PathVariable Long id) {
        return ResponseEntity.ok(stopMasterService.getStopById(id));
    }

    @GetMapping
    public ResponseEntity<List<StopMasterResponse>> getAllStops() {
        return ResponseEntity.ok(stopMasterService.getAllStops());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StopMasterResponse> updateStop(
            @PathVariable Long id,
            @RequestBody UpdateStopMasterRequest request) {
        return ResponseEntity.ok(stopMasterService.updateStop(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStop(@PathVariable Long id) {
        stopMasterService.deleteStop(id);
        return ResponseEntity.noContent().build();
    }
}
