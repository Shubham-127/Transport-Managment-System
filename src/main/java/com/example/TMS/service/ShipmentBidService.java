package com.example.TMS.service;


import com.example.TMS.dto.request.Create.CreateShipmentBidRequest;
import com.example.TMS.dto.response.ShipmentBidResponse;

import java.util.List;

public interface ShipmentBidService {
    ShipmentBidResponse placeBid(Long shipmentId, CreateShipmentBidRequest request);
    List<ShipmentBidResponse> getBidsForShipment(Long shipmentId);
    ShipmentBidResponse acceptBid(Long shipmentId, Long bidId);
}
