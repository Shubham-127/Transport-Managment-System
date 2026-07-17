package com.example.TMS.service;

import com.example.TMS.dto.request.Create.CreateBidDetailsRequest;
import com.example.TMS.dto.response.BidDetailsResponse;
import org.springframework.stereotype.Service;

@Service
public interface BidDetailsService {
    BidDetailsResponse createBidding(Long shipmentId, CreateBidDetailsRequest request);
    BidDetailsResponse getBiddingDetails(Long shipmentId);
}
