package com.example.UserCRUD.controller;

import com.example.UserCRUD.dto.request.CreateOrderMasterRequest;
import com.example.UserCRUD.dto.request.UpdateOrderMasterRequest;
import com.example.UserCRUD.dto.response.OrderMasterResponse;
import com.example.UserCRUD.service.OrderMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-master")
@RequiredArgsConstructor
public class OrderMasterController {

    private final OrderMasterService orderMasterService;

    @PostMapping
    public ResponseEntity<OrderMasterResponse> createOrder(@RequestBody CreateOrderMasterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderMasterService.createOrder(request));
    }

    @GetMapping
    public ResponseEntity<List<OrderMasterResponse>> getAllOrders() {
        return ResponseEntity.ok(orderMasterService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderMasterResponse> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderMasterService.getOrderById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderMasterResponse> updateOrder(
            @PathVariable Long id,
            @RequestBody UpdateOrderMasterRequest request) {
        return ResponseEntity.ok(orderMasterService.updateOrder(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderMasterService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}