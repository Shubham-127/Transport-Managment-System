package com.example.TMS.service;

import com.example.TMS.dto.request.Create.CreateVehicleMasterRequest;
import com.example.TMS.dto.request.Update.UpdateVehicleMasterRequest;
import com.example.TMS.dto.response.VehicleMasterResponse;

import java.util.List;

public interface VehicleMasterService {

    VehicleMasterResponse createVehicle(
            Long transportId,
            CreateVehicleMasterRequest request);

    VehicleMasterResponse getVehicleById(Long id);

    List<VehicleMasterResponse> getAllVehicles();

    VehicleMasterResponse updateVehicle(
            Long id,
            UpdateVehicleMasterRequest request);

    void deleteVehicle(Long id);
}
