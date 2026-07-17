package com.example.TMS.controller;

import com.example.TMS.dto.request.Create.CreateShipmentBidRequest;
import com.example.TMS.dto.response.ShipmentBidResponse;
import com.example.TMS.service.ShipmentBidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/shipments")
@RequiredArgsConstructor
public class ShipmentBidController {

    private final ShipmentBidService shipmentBidService;

    @PostMapping("/{shipmentId}/bids")
    public ResponseEntity<ShipmentBidResponse> placeBid(
            @PathVariable Long shipmentId,
            @RequestBody CreateShipmentBidRequest request) {
        return ResponseEntity.ok(shipmentBidService.placeBid(shipmentId, request));
    }

    @GetMapping("/{shipmentId}/bids")
    public ResponseEntity<List<ShipmentBidResponse>> getBidsForShipment(@PathVariable Long shipmentId) {
        return ResponseEntity.ok(shipmentBidService.getBidsForShipment(shipmentId));
    }

    @PostMapping("/{shipmentId}/bids/{bidId}/accept")
    public ResponseEntity<ShipmentBidResponse> acceptBid(
            @PathVariable Long shipmentId,
            @PathVariable Long bidId) {
        return ResponseEntity.ok(shipmentBidService.acceptBid(shipmentId, bidId));
    }
}
