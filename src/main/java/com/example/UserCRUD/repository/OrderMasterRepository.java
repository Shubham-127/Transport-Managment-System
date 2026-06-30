package com.example.UserCRUD.repository;

import com.example.UserCRUD.model.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderMasterRepository extends JpaRepository<OrderMaster, Long> {

    boolean existsByOrderNumber(Long orderNumber);
    Optional<OrderMaster> findByOrderNumber(Long orderNumber);
}