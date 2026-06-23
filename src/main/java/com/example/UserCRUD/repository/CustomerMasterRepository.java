package com.example.UserCRUD.repository;


import com.example.UserCRUD.model.CustomerMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerMasterRepository extends JpaRepository<CustomerMaster, Long> {
    Optional<CustomerMaster> findByCustomerId(Long customerId);
    boolean existsByCustomerId(Long customerId);
    boolean existsByEmail(String email);
}
