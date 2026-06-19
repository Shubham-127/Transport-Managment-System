package com.example.UserCRUD.repository;

import com.example.UserCRUD.model.OrderLinesMaster;
import com.example.UserCRUD.model.OrderLinesMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderLinesMasterRepository extends JpaRepository<OrderLinesMaster, Long> {

    // This is the KEY query for the one-to-many relationship —
    // "find every line whose orderMasterId matches this order's id"
    List<OrderLinesMaster> findByOrderMasterId(Long orderMasterId);
}
