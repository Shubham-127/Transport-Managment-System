package com.example.UserCRUD.dto.request.Create;



import lombok.*;

// No routeMasterId field here — the route's id comes from the
// URL path, not the request body (same pattern as Vehicle/Lines)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CreateStopMasterRequest {

    private Double distanceFromSource;
    private String stopName;
}
