package com.example.TMS.dto.response;


import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ShipmentMasterResponse {
    private Long id;
    private String shipmentNumber;
    private Long customerId;
    private String customerName;
    private String shipmentDate;
    private String status;
    private String remarks;
    // ShipmentMasterResponse.java — add this field
    private List<Long> orderIds;
    // ShipmentMasterResponse.java
    private Long routeId;

    private List<OrderWithLinesResponse> orders; // reusing your existing DTO
}