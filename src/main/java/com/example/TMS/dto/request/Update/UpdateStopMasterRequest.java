package com.example.TMS.dto.request.Update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class UpdateStopMasterRequest {

    private Long routeMasterId;
    private Integer stopSequence;
    private String stopLocation;
    private Double distanceFromPrevious;
    private String estimatedTimeFromPrevious;
}
