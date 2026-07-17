package com.example.TMS.repository;

import com.example.TMS.model.BidDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BidDetailsRepository extends JpaRepository<BidDetails, Long> {

    Optional<BidDetails> findByShipmentId(Long shipmentId);
}



