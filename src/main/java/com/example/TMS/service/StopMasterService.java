package com.example.TMS.service;

import com.example.TMS.dto.request.Create.CreateStopMasterRequest;
import com.example.TMS.dto.request.Update.UpdateStopMasterRequest;
import com.example.TMS.dto.response.StopMasterResponse;
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
