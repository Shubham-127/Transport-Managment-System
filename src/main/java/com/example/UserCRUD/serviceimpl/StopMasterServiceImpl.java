package com.example.UserCRUD.serviceimpl;



import com.example.UserCRUD.dto.request.Create.CreateStopMasterRequest;
import com.example.UserCRUD.dto.request.Update.UpdateStopMasterRequest;
import com.example.UserCRUD.dto.response.StopMasterResponse;
import com.example.UserCRUD.exception.ResourceNotFoundException;
import com.example.UserCRUD.model.RouteMaster;
import com.example.UserCRUD.model.StopMaster;
import com.example.UserCRUD.repository.RouteMasterRepository;
import com.example.UserCRUD.repository.StopMasterRepository;
import com.example.UserCRUD.service.StopMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service@RequiredArgsConstructor
public class StopMasterServiceImpl implements StopMasterService {

    private final StopMasterRepository stopMasterRepository;
    private final RouteMasterRepository routeMasterRepository;

    @Override
    public StopMasterResponse createStop(Long routeId, CreateStopMasterRequest request) {
        RouteMaster route = routeMasterRepository.findById(routeId)
                .orElseThrow(() -> new ResourceNotFoundException("Route not found with id: " + routeId));
        StopMaster stop = StopMaster.builder()
                .routeMaster(route)
                .distanceFromSource(request.getDistanceFromSource())
                .stopName(request.getStopName())
                .build();

        StopMaster saved = stopMasterRepository.save(stop);
        return mapToResponseDTO(saved);
    }



    @Override
    public StopMasterResponse getStopById(Long id) {
        StopMaster stop = stopMasterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stop not found with id: " + id));
        return mapToResponseDTO(stop);
    }

    @Override
    public List<StopMasterResponse> getAllStops() {
        return stopMasterRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StopMasterResponse updateStop(Long id, UpdateStopMasterRequest request) {
        StopMaster existing = stopMasterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stop not found with id: " + id));

        // carry over the SAME routeMaster — updating a stop's details
        // should never accidentally move it to a different route
        StopMaster updatedStop = StopMaster.builder()
                .id(existing.getId())
                .routeMaster(existing.getRouteMaster())
                .distanceFromSource(request.getDistanceFromSource())
                .stopName(request.getStopName())
                .build();

        StopMaster saved = stopMasterRepository.save(updatedStop);
        return mapToResponseDTO(saved);
    }

    @Override
    public void deleteStop(Long id) {
        StopMaster stop = stopMasterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stop not found with id: " + id));
        stopMasterRepository.delete(stop);
    }

    private StopMasterResponse mapToResponseDTO(StopMaster stop) {
        return StopMasterResponse.builder()
                .id(stop.getId())
                .routeMasterId(stop.getRouteMaster().getId())
                .routeCode(stop.getRouteMaster().getRouteCode())
                .distanceFromSource(stop.getDistanceFromSource())
                .stopName(stop.getStopName())
                .build();
    }
}
