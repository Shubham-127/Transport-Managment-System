package com.example.UserCRUD.dto.response;


import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class StopWithOrdersResponse {
    private Long id;
    private Integer stopSequence;
    private String stopLocation;
    private Double distanceFromPrevious;
    private String estimatedTimeFromPrevious;

    private List<OrderWithLinesResponse> orders;
}
