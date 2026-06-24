package com.example.UserCRUD.repository;

import com.example.UserCRUD.model.RouteMaster;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteMasterRepository extends JpaRepository<RouteMaster, Long> {
    boolean existsByRouteCode(String routeCode);



}
