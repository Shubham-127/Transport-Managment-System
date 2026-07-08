package com.example.TMS.repository;

import com.example.TMS.model.StopMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StopMasterRepository extends JpaRepository<StopMaster, Long> {

    List<StopMaster> findByRouteMasterId(Long routeMasterId);
}
