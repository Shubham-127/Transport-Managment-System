package com.example.TMS.repository;


import com.example.TMS.model.ShipmentMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShipmentMasterRepository extends JpaRepository<ShipmentMaster, Long> {
    boolean existsByShipmentNumber(String shipmentNumber);
    List<ShipmentMaster> findByCustomerId(Long customerId);

}
