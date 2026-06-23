package com.example.UserCRUD.serviceimpl;


import com.example.UserCRUD.dto.request.Create.CreateVehicleMasterRequest;
import com.example.UserCRUD.dto.request.Update.UpdateVehicleMasterRequest;
import com.example.UserCRUD.dto.response.VehicleMasterResponse;
import com.example.UserCRUD.exception.ResourceNotFoundException;
import com.example.UserCRUD.model.TransportMaster;
import com.example.UserCRUD.model.VehicleMaster;
import com.example.UserCRUD.repository.TransportMasterRepository;
import com.example.UserCRUD.repository.VehicleMasterRepository;
import com.example.UserCRUD.service.VehicleMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleMasterServiceImpl implements VehicleMasterService {

    private final VehicleMasterRepository vehicleMasterRepository;

    // needed to confirm the parent transport actually exists
    // before attaching a vehicle to it
    private final TransportMasterRepository transportMasterRepository;

    @Override
    public VehicleMasterResponse createVehicle(Long transportId, CreateVehicleMasterRequest request) {

        // Step 1 — confirm the parent transport company actually exists
        TransportMaster transport = transportMasterRepository.findById(transportId)
                .orElseThrow(() -> new ResourceNotFoundException("Transport not found with id: " + transportId));

        // Business rule — block duplicate vehicleNumber
        if (vehicleMasterRepository.existsByVehicleNumber(request.getVehicleNumber())) {
            throw new RuntimeException("Vehicle already exists with vehicleNumber: " + request.getVehicleNumber());
        }

        // Step 2 — build the vehicle, explicitly linking it to the parent transport
        VehicleMaster vehicle = VehicleMaster.builder()
                .transportMaster(transport)
                .vehicleNumber(request.getVehicleNumber())
                .vehicleType(request.getVehicleType())
                .maxWeightCapacity(request.getMaxWeightCapacity())
                .maxVolumeCapacity(request.getMaxVolumeCapacity())
                .gpsDeviceId(request.getGpsDeviceId())
                .status(request.getStatus())
                .build();

        VehicleMaster saved = vehicleMasterRepository.save(vehicle);
        return mapToResponseDTO(saved);
    }

    @Override
    public VehicleMasterResponse getVehicleById(Long id) {
        VehicleMaster vehicle = vehicleMasterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
        return mapToResponseDTO(vehicle);
    }

    @Override
    public List<VehicleMasterResponse> getAllVehicles() {
        // findAll() returns EVERY vehicle across ALL transport companies —
        // matches your interface's actual signature, no transportId filter
        return vehicleMasterRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VehicleMasterResponse updateVehicle(Long id, UpdateVehicleMasterRequest request) {

        VehicleMaster existing = vehicleMasterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));

        // carry over the SAME transportMaster — updating a vehicle's
        // details should never accidentally move it to a different
        // transport company
        VehicleMaster updatedVehicle = VehicleMaster.builder()
                .id(existing.getId())
                .transportMaster(existing.getTransportMaster())
                .vehicleNumber(request.getVehicleNumber())
                .vehicleType(request.getVehicleType())
                .maxWeightCapacity(request.getMaxWeightCapacity())
                .maxVolumeCapacity(request.getMaxVolumeCapacity())
                .gpsDeviceId(request.getGpsDeviceId())
                .status(request.getStatus())
                .build();

        VehicleMaster saved = vehicleMasterRepository.save(updatedVehicle);
        return mapToResponseDTO(saved);
    }

    @Override
    public void deleteVehicle(Long id) {
        VehicleMaster vehicle = vehicleMasterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
        vehicleMasterRepository.delete(vehicle);
    }

    private VehicleMasterResponse mapToResponseDTO(VehicleMaster vehicle) {
        return VehicleMasterResponse.builder()
                .id(vehicle.getId())
                .transportMasterId(vehicle.getTransportMaster().getId())
                .vehicleNumber(vehicle.getVehicleNumber())
                .vehicleType(vehicle.getVehicleType())
                .maxWeightCapacity(vehicle.getMaxWeightCapacity())
                .maxVolumeCapacity(vehicle.getMaxVolumeCapacity())
                .gpsDeviceId(vehicle.getGpsDeviceId())
                .status(vehicle.getStatus())
                .build();
    }
}