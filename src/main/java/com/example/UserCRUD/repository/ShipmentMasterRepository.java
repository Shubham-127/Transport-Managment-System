package com.example.UserCRUD.repository;


import com.example.UserCRUD.model.ShipmentMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShipmentMasterRepository extends JpaRepository<ShipmentMaster, Long> {
    boolean existsByShipmentNumber(String shipmentNumber);
    List<ShipmentMaster> findByCustomerId(Long customerId);

}
