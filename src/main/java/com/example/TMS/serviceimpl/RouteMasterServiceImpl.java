package com.example.TMS.serviceimpl;

import com.example.TMS.dto.request.Create.CreateRouteMasterRequest;
import com.example.TMS.dto.request.Update.UpdateRouteMasterRequest;
import com.example.TMS.dto.response.RouteMasterResponse;
import com.example.TMS.exception.ResourceNotFoundException;
import com.example.TMS.model.RouteMaster;
import com.example.TMS.repository.RouteMasterRepository;
import com.example.TMS.service.RouteMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service@RequiredArgsConstructor
public class RouteMasterServiceImpl implements RouteMasterService {

    public final RouteMasterRepository routeMasterRepository;
    @Override
    public RouteMasterResponse createRoute(CreateRouteMasterRequest request) {
        if(routeMasterRepository.existsByRouteCode(request.getRouteCode())){
            throw new RuntimeException("Route already exists with routeCode:" +request.getRouteCode());

        }
        RouteMaster route = RouteMaster.builder()
                .routeCode(request.getRouteCode())
                .sourceLocation(request.getSourceLocation())
                .destinationLocation(request.getDestinationLocation())
                .distance(request.getDistance())
                .estimatedTime(request.getEstimatedTime())
                .build();

        RouteMaster saved = routeMasterRepository.save(route);
        return mapToResponseDTO(saved);
    }

    @Override
    public List<RouteMasterResponse> getAllRoutes() {
        return routeMasterRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RouteMasterResponse getRouteById(Long id) {
        RouteMaster route = routeMasterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Route not found with id: " + id));
        return mapToResponseDTO(route);
    }

    @Override
    public RouteMasterResponse updateRoute(Long id, UpdateRouteMasterRequest request) {
        RouteMaster existing = routeMasterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Route not found with id: " + id));

        RouteMaster updatedRoute = RouteMaster.builder()
                .id(existing.getId())
                .routeCode(request.getRouteCode())
                .sourceLocation(request.getSourceLocation())
                .destinationLocation(request.getDestinationLocation())
                .distance(request.getDistance())
                .estimatedTime(request.getEstimatedTime())
                .build();

        RouteMaster saved = routeMasterRepository.save(updatedRoute);
        return mapToResponseDTO(saved);
    }

    @Override
    public void deleteRoute(Long id) {
        RouteMaster route = routeMasterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Route not found with id: " + id));
        routeMasterRepository.delete(route);
    }

    private RouteMasterResponse mapToResponseDTO(RouteMaster route) {
        return RouteMasterResponse.builder()
                .id(route.getId())
                .routeCode(route.getRouteCode())
                .sourceLocation(route.getSourceLocation())
                .destinationLocation(route.getDestinationLocation())
                .distance(route.getDistance())
                .estimatedTime(route.getEstimatedTime())
                .build();
    }
}
