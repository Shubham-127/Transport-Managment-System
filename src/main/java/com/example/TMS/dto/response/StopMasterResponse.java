package com.example.TMS.dto.response;

import lombok.*;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor@Builder
public class StopMasterResponse {
    private Long id;
    private Long routeMasterId;
    private Integer stopSequence;
    private String stopLocation;
    private Double distanceFromPrevious;
    private String estimatedTimeFromPrevious;
    private String routeCode;
}
