package com.example.UserCRUD.repository;

import com.example.UserCRUD.model.StopMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StopMasterRepository extends JpaRepository<StopMaster, Long> {

    List<StopMaster> findByRouteMasterId(Long routeMasterId);
}
