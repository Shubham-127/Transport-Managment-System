package com.example.UserCRUD.service;

import com.example.UserCRUD.dto.request.Create.CreateShipmentMasterRequest;
import com.example.UserCRUD.dto.response.ShipmentMasterResponse;

import java.util.List;

public interface ShipmentMasterService {
    ShipmentMasterResponse createShipment(CreateShipmentMasterRequest request);
    ShipmentMasterResponse getShipmentById(Long id);
    List<ShipmentMasterResponse> getAllShipments();
    List<ShipmentMasterResponse> getShipmentsByCustomer(Long customerId);
    ShipmentMasterResponse addOrdersToShipment(Long shipmentId, CreateShipmentMasterRequest request);

}