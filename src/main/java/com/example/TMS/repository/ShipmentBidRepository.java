package com.example.TMS.repository;

import com.example.TMS.model.ShipmentBid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipmentBidRepository extends JpaRepository<ShipmentBid, Long > {
    List<ShipmentBid> findByShipmentId(Long shipmentId);
    boolean existsByShipmentIdAndStatus(Long shipmentId, String status);

}
