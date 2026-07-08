package com.example.TMS.serviceimpl;



import com.example.TMS.dto.request.Create.CreateStopMasterRequest;
import com.example.TMS.dto.request.Update.UpdateStopMasterRequest;
import com.example.TMS.dto.response.StopMasterResponse;
import com.example.TMS.exception.ResourceNotFoundException;
import com.example.TMS.model.RouteMaster;
import com.example.TMS.model.StopMaster;
import com.example.TMS.repository.RouteMasterRepository;
import com.example.TMS.repository.StopMasterRepository;
import com.example.TMS.service.StopMasterService;
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
                .stopSequence(request.getStopSequence())
                .stopLocation(request.getStopLocation())
                .distanceFromPrevious(request.getDistanceFromPrevious())
                .estimatedTimeFromPrevious(request.getEstimatedTimeFromPrevious())
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
                .stopSequence(request.getStopSequence())
                .stopLocation(request.getStopLocation())
                .distanceFromPrevious(request.getDistanceFromPrevious())
                .estimatedTimeFromPrevious(request.getEstimatedTimeFromPrevious())
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
                .stopSequence(stop.getStopSequence())
                .stopLocation(stop.getStopLocation())
                .distanceFromPrevious(stop.getDistanceFromPrevious())
                .estimatedTimeFromPrevious(stop.getEstimatedTimeFromPrevious())
                .build();
    }
}
