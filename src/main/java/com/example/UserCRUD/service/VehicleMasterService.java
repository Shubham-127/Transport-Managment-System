package com.example.UserCRUD.service;

import com.example.UserCRUD.dto.request.Create.CreateVehicleMasterRequest;
import com.example.UserCRUD.dto.request.Update.UpdateVehicleMasterRequest;
import com.example.UserCRUD.dto.response.VehicleMasterResponse;

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
