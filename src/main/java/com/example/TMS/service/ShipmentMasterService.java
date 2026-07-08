package com.example.TMS.service;

import com.example.TMS.dto.request.Create.CreateShipmentMasterRequest;
import com.example.TMS.dto.response.ShipmentMasterResponse;

import java.util.List;

public interface ShipmentMasterService {
    ShipmentMasterResponse createShipment(CreateShipmentMasterRequest request);
    ShipmentMasterResponse getShipmentById(Long id);
    List<ShipmentMasterResponse> getAllShipments();
    List<ShipmentMasterResponse> getShipmentsByCustomer(Long customerId);
    ShipmentMasterResponse addOrdersToShipment(Long shipmentId, CreateShipmentMasterRequest request);

}