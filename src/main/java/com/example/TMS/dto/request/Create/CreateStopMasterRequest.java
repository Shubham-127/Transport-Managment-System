package com.example.TMS.dto.request.Create;



import lombok.*;

// No routeMasterId field here — the route's id comes from the
// URL path, not the request body (same pattern as Vehicle/Lines)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CreateStopMasterRequest {
    private Long routeMasterId;
    private Integer stopSequence;
    private String stopLocation;
    private Double distanceFromPrevious;
    private String estimatedTimeFromPrevious;
}
