package com.example.UserCRUD.service;


import com.example.UserCRUD.dto.request.Create.CreateRouteMasterRequest;
import com.example.UserCRUD.dto.request.Update.UpdateRouteMasterRequest;
import com.example.UserCRUD.dto.response.RouteMasterResponse;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface RouteMasterService {

    RouteMasterResponse createRoute(CreateRouteMasterRequest request);
    List<RouteMasterResponse> getAllRoutes();
    RouteMasterResponse getRouteById(Long id);
    RouteMasterResponse updateRoute(Long id, UpdateRouteMasterRequest request);
    void deleteRoute(Long id);
}
