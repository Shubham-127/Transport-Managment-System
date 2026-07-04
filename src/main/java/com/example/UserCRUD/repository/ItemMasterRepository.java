package com.example.UserCRUD.repository;


import com.example.UserCRUD.model.ItemMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemMasterRepository extends JpaRepository<ItemMaster, Long> {
    boolean existsByItemNumber(String itemNumber);


    Optional<ItemMaster> findByItemNumber(String itemNumber);
}
