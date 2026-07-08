package com.example.TMS.repository;

import com.example.TMS.model.RouteMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteMasterRepository extends JpaRepository<RouteMaster, Long> {
    boolean existsByRouteCode(String routeCode);



}
