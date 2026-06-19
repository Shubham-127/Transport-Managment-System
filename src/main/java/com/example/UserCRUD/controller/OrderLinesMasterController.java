package com.example.UserCRUD.controller;

import com.example.UserCRUD.dto.request.CreateOrderLinesMasterRequest;
import com.example.UserCRUD.dto.request.UpdateOrderLinesMasterRequest;
import com.example.UserCRUD.dto.response.OrderLinesMasterResponse;
import com.example.UserCRUD.dto.response.OrderLinesMasterResponse;
import com.example.UserCRUD.repository.OrderLinesMasterRepository;
import com.example.UserCRUD.service.OrderLinesMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-lines")
@RequiredArgsConstructor
public class OrderLinesMasterController {

    private final OrderLinesMasterService orderLineMasterService;

    // POST /api/order-lines/{orderId} — add a line to a specific order
    // orderId comes from the URL path, not the request body
    @PostMapping("/{orderId}")
    public ResponseEntity<OrderLinesMasterResponse> createLine(
            @PathVariable Long orderId,
            @RequestBody CreateOrderLinesMasterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderLineMasterService.createLine(orderId, request));
    }

    // GET /api/order-lines/order/{orderId} — get ALL lines for one order
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderLinesMasterResponse>> getLinesByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderLineMasterService.getLinesByOrderId(orderId));
    }

    // GET /api/order-lines/{id} — get one specific line by its own id
    @GetMapping("/{id}")
    public ResponseEntity<OrderLinesMasterResponse> getLineById(@PathVariable Long id) {
        return ResponseEntity.ok(orderLineMasterService.getLineById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderLinesMasterResponse> updateLine(
            @PathVariable Long id,
            @RequestBody UpdateOrderLinesMasterRequest request) {
        return ResponseEntity.ok(orderLineMasterService.updateLine(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLine(@PathVariable Long id) {
        orderLineMasterService.deleteLine(id);
        return ResponseEntity.noContent().build();
    }
}
