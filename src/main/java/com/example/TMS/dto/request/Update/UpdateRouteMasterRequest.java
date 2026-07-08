package com.example.TMS.dto.request.Update;


import lombok.*;

@Getter
@Setter @NoArgsConstructor @AllArgsConstructor
public class UpdateRouteMasterRequest {

    private String routeCode;
    private String sourceLocation;
    private String destinationLocation;
    private Double distance;
    private String estimatedTime;
}
