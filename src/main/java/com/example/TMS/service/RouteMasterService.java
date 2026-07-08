package com.example.TMS.service;


import com.example.TMS.dto.request.Create.CreateRouteMasterRequest;
import com.example.TMS.dto.request.Update.UpdateRouteMasterRequest;
import com.example.TMS.dto.response.RouteMasterResponse;
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
