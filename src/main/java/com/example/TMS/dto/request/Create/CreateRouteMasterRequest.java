package com.example.TMS.dto.request.Create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class CreateRouteMasterRequest {

    private String routeCode;
    private String sourceLocation;
    private String destinationLocation;
    private Double distance;
    private String estimatedTime;
}
