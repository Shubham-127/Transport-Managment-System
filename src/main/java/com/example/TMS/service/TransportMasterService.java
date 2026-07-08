package com.example.TMS.service;



import com.example.TMS.dto.request.Create.CreateTransportMasterRequest;
import com.example.TMS.dto.request.Update.UpdateTransportMasterRequest;
import com.example.TMS.dto.response.TransportMasterResponse;
import java.util.List;

public interface TransportMasterService {

    TransportMasterResponse createTransport(CreateTransportMasterRequest request);

    List<TransportMasterResponse> getAllTransports();

    TransportMasterResponse getTransportById(Long id);

    TransportMasterResponse updateTransport(Long id, UpdateTransportMasterRequest request);

    void deleteTransport(Long id);
}