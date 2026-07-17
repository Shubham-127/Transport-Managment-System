package com.example.TMS.serviceimpl;

import com.example.TMS.dto.request.Create.CreateShipmentBidRequest;
import com.example.TMS.dto.response.ShipmentBidResponse;
import com.example.TMS.exception.ResourceNotFoundException;
import com.example.TMS.model.OrderMaster;
import com.example.TMS.model.ShipmentBid;
import com.example.TMS.model.ShipmentMaster;
import com.example.TMS.model.TransportMaster;
import com.example.TMS.repository.OrderMasterRepository;
import com.example.TMS.repository.ShipmentBidRepository;
import com.example.TMS.repository.ShipmentMasterRepository;
import com.example.TMS.repository.TransportMasterRepository;
import com.example.TMS.service.ShipmentBidService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShipmentBidServiceImpl implements ShipmentBidService {

    private final ShipmentBidRepository shipmentBidRepository;
    private final ShipmentMasterRepository shipmentMasterRepository;
    private final TransportMasterRepository transportMasterRepository;
    private final OrderMasterRepository orderMasterRepository;

    private static final String STATUS_PENDING = "PENDING";
    private static final String STATUS_ACCEPTED = "ACCEPTED";
    private static final String STATUS_REJECTED = "REJECTED";

    private static final String SHIPMENT_STATUS_OPEN_FOR_BIDDING = "OPEN_FOR_BIDDING";
    private static final String SHIPMENT_STATUS_AWARDED = "AWARDED";

    @Override
    @Transactional
    public ShipmentBidResponse placeBid(Long shipmentId, CreateShipmentBidRequest request) {

        // Step 1 — shipment must exist
        ShipmentMaster shipment = shipmentMasterRepository.findById(shipmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found with id: " + shipmentId));

        // Step 2 — bidding must currently be OPEN
        if (!SHIPMENT_STATUS_OPEN_FOR_BIDDING.equalsIgnoreCase(shipment.getStatus())) {
            throw new RuntimeException("This shipment is not currently open for bidding. Current status: " + shipment.getStatus());
        }

        // Step 3 — transporter must exist
        TransportMaster transporter = transportMasterRepository.findById(request.getTransporterId())
                .orElseThrow(() -> new ResourceNotFoundException("Transporter not found with id: " + request.getTransporterId()));

        // Step 4 — create the bid as PENDING
        ShipmentBid bid = ShipmentBid.builder()
                .shipment(shipment)
                .transport(transporter)
                .charges(request.getCharges())
                .deliveryDays(request.getDeliveryDays())
                .offeredVehicleCapacity(request.getOfferedVehicleCapacity())
                .remarks(request.getRemarks())
                .status(STATUS_PENDING)
                .build();

        ShipmentBid savedBid = shipmentBidRepository.save(bid);

        return mapToResponseDTO(savedBid);
    }

    @Override
    public List<ShipmentBidResponse> getBidsForShipment(Long shipmentId) {

        shipmentMasterRepository.findById(shipmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found with id: " + shipmentId));

        return shipmentBidRepository.findByShipmentId(shipmentId)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ShipmentBidResponse acceptBid(Long shipmentId, Long bidId) {

        // Step 1 — shipment must exist and currently be open for bidding
        ShipmentMaster shipment = shipmentMasterRepository.findById(shipmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found with id: " + shipmentId));

        if (!SHIPMENT_STATUS_OPEN_FOR_BIDDING.equalsIgnoreCase(shipment.getStatus())) {
            throw new RuntimeException("Cannot accept a bid — shipment is not open for bidding. Current status: " + shipment.getStatus());
        }

        // Step 2 — fetch ALL bids for this shipment
        List<ShipmentBid> allBids = shipmentBidRepository.findByShipmentId(shipmentId);

        // Step 3 — the bid being accepted MUST belong to this shipment
        ShipmentBid bidToAccept = allBids.stream()
                .filter(b -> b.getId().equals(bidId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Bid not found with id: " + bidId + " for shipment: " + shipmentId));

        // Step 4 — accept this one, reject every other bid for the same shipment
        allBids.forEach(b -> {
            if (b.getId().equals(bidId)) {
                b.setStatus(STATUS_ACCEPTED);
            } else {
                b.setStatus(STATUS_REJECTED);
            }
        });
        shipmentBidRepository.saveAll(allBids);

        // Step 5 — close bidding permanently by marking the shipment AWARDED
        shipment.setStatus(SHIPMENT_STATUS_AWARDED);
        shipmentMasterRepository.save(shipment);

        List<OrderMaster> orders = orderMasterRepository.findByShipmentId(shipmentId);
        orders.forEach(order -> order.setStatus("BIDDING_DONE"));
        orderMasterRepository.saveAll(orders);


        return mapToResponseDTO(bidToAccept);
    }

    private ShipmentBidResponse mapToResponseDTO(ShipmentBid bid) {
        return ShipmentBidResponse.builder()
                .id(bid.getId())
                .shipmentId(bid.getShipment().getId())
                .transportId(bid.getTransport().getId())
                .companyName(bid.getTransport().getCompanyName())
                .charges(bid.getCharges())
                .deliveryDays(bid.getDeliveryDays())
                .offeredVehicleCapacity(bid.getOfferedVehicleCapacity())
                .remarks(bid.getRemarks())
                .status(bid.getStatus())
                .createdAt(bid.getCreatedAt())
                .updatedAt(bid.getUpdatedAt())
                .build();
    }
}