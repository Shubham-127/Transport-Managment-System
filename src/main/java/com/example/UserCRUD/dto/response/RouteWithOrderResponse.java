package com.example.UserCRUD.dto.response;
import java.util.List;
import lombok.*;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RouteWithOrderResponse {
    private Long id;
    private String routeCode;
    private String sourceLocation;
    private String destinationLocation;
    private Double distance;
    private String estimatedTime;

    private List<StopWithOrdersResponse> stops;
}
