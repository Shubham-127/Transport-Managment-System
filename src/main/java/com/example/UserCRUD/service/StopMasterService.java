package com.example.UserCRUD.service;

import com.example.UserCRUD.dto.request.Create.CreateStopMasterRequest;
import com.example.UserCRUD.dto.request.Update.UpdateStopMasterRequest;
import com.example.UserCRUD.dto.response.StopMasterResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StopMasterService {
    StopMasterResponse createStop(Long routeId , CreateStopMasterRequest request);
    List<StopMasterResponse> getAllStops();
    StopMasterResponse getStopById(Long id);
    StopMasterResponse updateStop(Long id, UpdateStopMasterRequest request);
    void deleteStop(Long id);

}
