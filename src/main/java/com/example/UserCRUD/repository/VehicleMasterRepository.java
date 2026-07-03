package com.example.UserCRUD.repository;

import com.example.UserCRUD.model.VehicleMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleMasterRepository extends JpaRepository<VehicleMaster, Long> {

    boolean existsByVehicleNumber(String vehicleNumber);
    Optional<VehicleMaster> findByVehicleNumber(String vehicleNumber);

    // "TransportMaster" refers to the related entity field name
    // "Id" tells JPA to drill into that entity and match its id —
    // generates: SELECT * FROM vehicle_master WHERE transport_master_id = ?
    List<VehicleMaster> findByTransportMasterId(Long transportMasterId);
}
