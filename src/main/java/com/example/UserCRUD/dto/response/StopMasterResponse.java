package com.example.UserCRUD.dto.response;

import lombok.*;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor@Builder
public class StopMasterResponse {
    private Long id;
    private Long routeMasterId;
    private String routeCode;
    private Double distanceFromSource;
    private String stopName;
}
