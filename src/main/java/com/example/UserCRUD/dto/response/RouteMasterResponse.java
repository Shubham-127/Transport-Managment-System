package com.example.UserCRUD.dto.response;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RouteMasterResponse {

    private Long id;
    private String routeCode;
    private String sourceLocation;
    private String destinationLocation;
    private Double distance;
    private String estimatedTime;
}