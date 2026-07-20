package com.example.TMS.controller;

import com.example.TMS.dto.request.Create.CreateBidDetailsRequest;
import com.example.TMS.dto.response.BidDetailsResponse;
import com.example.TMS.service.BidDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Shipment")
@RequiredArgsConstructor
public class BidDetailController {
    public final BidDetailsService bidDetailsService;

    @PostMapping("/{shipmentId}/open-bidding")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BidDetailsResponse> createBidding(@PathVariable Long shipmentId, @RequestBody CreateBidDetailsRequest request){
        return ResponseEntity.ok(bidDetailsService.createBidding(shipmentId, request));
    }

    @GetMapping("/{shipmentId}/bidding-Details")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BidDetailsResponse> getBiddingDetails(@PathVariable Long shipmentId){
        return ResponseEntity.ok(bidDetailsService.getBiddingDetails(shipmentId));
    }

}
