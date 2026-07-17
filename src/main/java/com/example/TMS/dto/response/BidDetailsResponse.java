package com.example.TMS.dto.response;

import com.example.TMS.model.ShipmentMaster;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor@Builder
public class BidDetailsResponse {
    private Long id;
    private Double expectedAmount;
    private String expectedDeliveryDays;
    private Double vehicleCapacity;
    private String remarks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long shipmentId;

}
