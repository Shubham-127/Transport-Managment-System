package com.example.UserCRUD.repository;

import com.example.UserCRUD.model.TransportMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransportMasterRepository extends JpaRepository<TransportMaster, Long> {

    boolean existsByGstNumber(String gstNumber);
    Optional<TransportMaster> findByAddressNumber(Integer addressNumber);
}