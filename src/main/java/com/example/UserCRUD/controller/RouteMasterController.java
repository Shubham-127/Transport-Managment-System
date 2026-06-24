package com.example.UserCRUD.controller;

import com.example.UserCRUD.dto.request.Create.CreateRouteMasterRequest;
import com.example.UserCRUD.dto.request.Update.UpdateRouteMasterRequest;
import com.example.UserCRUD.dto.response.RouteMasterResponse;
import com.example.UserCRUD.service.RouteMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
public class RouteMasterController {

    private final RouteMasterService routeMasterService;

    @PostMapping
    public ResponseEntity<RouteMasterResponse> createRoute(@RequestBody CreateRouteMasterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(routeMasterService.createRoute(request));
    }

    @GetMapping
    public ResponseEntity<List<RouteMasterResponse>> getAllRoutes() {
        return ResponseEntity.ok(routeMasterService.getAllRoutes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteMasterResponse> getRouteById(@PathVariable Long id) {
        return ResponseEntity.ok(routeMasterService.getRouteById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RouteMasterResponse> updateRoute(
            @PathVariable Long id,
            @RequestBody UpdateRouteMasterRequest request) {
        return ResponseEntity.ok(routeMasterService.updateRoute(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        routeMasterService.deleteRoute(id);
        return ResponseEntity.noContent().build();
    }
}
