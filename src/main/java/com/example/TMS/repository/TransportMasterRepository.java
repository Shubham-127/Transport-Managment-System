package com.example.TMS.repository;

import com.example.TMS.model.TransportMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransportMasterRepository extends JpaRepository<TransportMaster, Long> {

    boolean existsByGstNumber(String gstNumber);
    Optional<TransportMaster> findByAddressNumber(Integer addressNumber);
}