package com.example.TMS.dto.response;

import java.time.LocalDateTime;

import lombok.*;

@Getter @Setter @NoArgsConstructor
@AllArgsConstructor @Builder
public class ShipmentBidResponse {
    private Long id;
    private Long shipmentId;
    private Long transportId;
    private String companyName;
    private Double charges;
    private Integer deliveryDays;
    private Double offeredVehicleCapacity;
    private String remarks;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
