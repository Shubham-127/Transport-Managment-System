package com.example.TMS.serviceimpl;

import com.example.TMS.dto.request.Create.CreateBidDetailsRequest;
import com.example.TMS.dto.response.BidDetailsResponse;
import com.example.TMS.exception.ResourceNotFoundException;
import com.example.TMS.model.BidDetails;
import com.example.TMS.model.OrderMaster;
import com.example.TMS.model.ShipmentMaster;
import com.example.TMS.repository.BidDetailsRepository;
import com.example.TMS.repository.OrderMasterRepository;
import com.example.TMS.repository.ShipmentBidRepository;
import com.example.TMS.repository.ShipmentMasterRepository;
import com.example.TMS.service.BidDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BidDetailsServiceImpl implements BidDetailsService {
    public final BidDetailsRepository bidDetailsRepository;
    public final ShipmentMasterRepository shipmentMasterRepository;
    public final OrderMasterRepository orderMasterRepository;

    @Override
    @Transactional
    public BidDetailsResponse createBidding(Long shipmentId, CreateBidDetailsRequest request){
        ShipmentMaster shipment = shipmentMasterRepository.findById( shipmentId)
                .orElseThrow(()-> new ResourceNotFoundException("Shipment not found " +shipmentId));

        if(!"CREATED".equalsIgnoreCase(shipment.getStatus())){
            throw new RuntimeException("Only a shipment with status CREATED can be opened for bidding. Current status: " + shipment.getStatus());
        }

        // Request
        BidDetails details = BidDetails.builder()
                .shipment(shipment)
                .expectedAmount(request.getExpectedAmount())
                .expectedDeliveryDays(request.getExpectedDeliveryDays())
                .vehicleCapacity(request.getVehicleCapacity())
                .remarks(request.getRemarks())
                .build();

        BidDetails savedDetails = bidDetailsRepository.save(details);

        shipment.setStatus("Open_For_Bidding");
        shipmentMasterRepository.save(shipment);

       List<OrderMaster> orders = orderMasterRepository.findByShipmentId(shipmentId);
        orders.forEach(order -> order.setStatus("OPEN_FOR_BID"));
        orderMasterRepository.saveAll(orders);



        return mapToResponseDto(savedDetails);
    }

    @Override
    public BidDetailsResponse getBiddingDetails(Long shipmentId){
        BidDetails details = bidDetailsRepository.findByShipmentId(shipmentId)
                .orElseThrow(()->new ResourceNotFoundException("Bidding Details not found for the Shipment id:" + shipmentId));

        return mapToResponseDto(details);
    }

    public BidDetailsResponse mapToResponseDto(BidDetails details){
        return BidDetailsResponse.builder()
                .id(details.getId())
                .shipmentId(details.getShipment().getId())
                .expectedAmount(details.getExpectedAmount())
                .expectedDeliveryDays(details.getExpectedDeliveryDays())
                .vehicleCapacity(details.getVehicleCapacity())
                .remarks(details.getRemarks())
                .createdAt(details.getCreatedAt())
                .updatedAt(details.getUpdatedAt())
                .build();
    }
}
