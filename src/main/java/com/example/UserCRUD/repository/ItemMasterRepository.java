package com.example.UserCRUD.repository;


import com.example.UserCRUD.model.ItemMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemMasterRepository extends JpaRepository<ItemMaster, Long> {
    boolean existsByItemNumber(String itemNumber);

}
