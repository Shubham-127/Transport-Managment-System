package com.example.UserCRUD.service;



import com.example.UserCRUD.dto.request.Create.CreateTransportMasterRequest;
import com.example.UserCRUD.dto.request.Update.UpdateTransportMasterRequest;
import com.example.UserCRUD.dto.response.TransportMasterResponse;
import java.util.List;

public interface TransportMasterService {

    TransportMasterResponse createTransport(CreateTransportMasterRequest request);

    List<TransportMasterResponse> getAllTransports();

    TransportMasterResponse getTransportById(Long id);

    TransportMasterResponse updateTransport(Long id, UpdateTransportMasterRequest request);

    void deleteTransport(Long id);
}