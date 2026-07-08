package com.example.TMS.serviceimpl;


import com.example.TMS.dto.request.Create.CreateTransportMasterRequest;
import com.example.TMS.dto.request.Update.UpdateTransportMasterRequest;
import com.example.TMS.dto.response.TransportMasterResponse;
import com.example.TMS.exception.ResourceNotFoundException;
import com.example.TMS.model.TransportMaster;
import com.example.TMS.repository.TransportMasterRepository;
import com.example.TMS.service.TransportMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransportMasterServiceImpl implements TransportMasterService {

    private final TransportMasterRepository transportMasterRepository;

    @Override
    public TransportMasterResponse createTransport(CreateTransportMasterRequest request) {

        if (transportMasterRepository.existsByGstNumber(request.getGstNumber())) {
            throw new RuntimeException("Transport already exists with gstNumber: " + request.getGstNumber());
        }

        TransportMaster transport = TransportMaster.builder()
                .companyName(request.getCompanyName())
                .gstNumber(request.getGstNumber())
                .panNumber(request.getPanNumber())
                .contactPerson(request.getContactPerson())
                .mobile(request.getMobile())
                .email(request.getEmail())
                .address(request.getAddress())
                .city(request.getCity())
                .state(request.getState())
                .pincode(request.getPincode())
                .active(request.getActive())
                .remarks(request.getRemarks())
                .addressNumber(request.getAddressNumber())
                .build();

        TransportMaster saved = transportMasterRepository.save(transport);
        return mapToResponseDTO(saved);
    }

    @Override
    public List<TransportMasterResponse> getAllTransports() {
        return transportMasterRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TransportMasterResponse getTransportById(Long id) {
        TransportMaster transport = transportMasterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transport not found with id: " + id));
        return mapToResponseDTO(transport);
    }

    @Override
    public TransportMasterResponse updateTransport(Long id, UpdateTransportMasterRequest request) {
        TransportMaster existing = transportMasterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transport not found with id: " + id));

        TransportMaster updatedTransport = TransportMaster.builder()
                .id(existing.getId())
                .companyName(request.getCompanyName())
                .gstNumber(request.getGstNumber())
                .panNumber(request.getPanNumber())
                .contactPerson(request.getContactPerson())
                .mobile(request.getMobile())
                .email(request.getEmail())
                .address(request.getAddress())
                .city(request.getCity())
                .state(request.getState())
                .pincode(request.getPincode())
                .active(request.getActive())
                .remarks(request.getRemarks())
                .addressNumber(request.getAddressNumber())
                .build();

        TransportMaster saved = transportMasterRepository.save(updatedTransport);
        return mapToResponseDTO(saved);
    }

    @Override
    public void deleteTransport(Long id) {
        TransportMaster transport = transportMasterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transport not found with id: " + id));
        transportMasterRepository.delete(transport);
    }

    private TransportMasterResponse mapToResponseDTO(TransportMaster transport) {
        return TransportMasterResponse.builder()
                .id(transport.getId())
                .companyName(transport.getCompanyName())
                .gstNumber(transport.getGstNumber())
                .panNumber(transport.getPanNumber())
                .contactPerson(transport.getContactPerson())
                .mobile(transport.getMobile())
                .email(transport.getEmail())
                .address(transport.getAddress())
                .city(transport.getCity())
                .state(transport.getState())
                .pincode(transport.getPincode())
                .active(transport.getActive())
                .remarks(transport.getRemarks())
                .addressNumber(transport.getAddressNumber())
                .build();
    }
}