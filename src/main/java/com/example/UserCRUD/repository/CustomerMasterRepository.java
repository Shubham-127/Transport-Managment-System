package com.example.UserCRUD.repository;


import com.example.UserCRUD.model.CustomerMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerMasterRepository extends JpaRepository<CustomerMaster, Long> {

    Optional<CustomerMaster> findByName(String customerName);
    boolean existsByName(String customerName);
    boolean existsByCustomerId(Long customerId);
}
