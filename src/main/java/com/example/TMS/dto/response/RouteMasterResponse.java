package com.example.TMS.dto.response;

import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RouteMasterResponse {

    private Long id;
    private String routeCode;
    private String sourceLocation;
    private String destinationLocation;
    private Double distance;
    private String estimatedTime;
    private List<StopMasterResponse> stops;
}