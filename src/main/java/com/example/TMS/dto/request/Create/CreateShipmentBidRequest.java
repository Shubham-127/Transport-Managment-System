package com.example.TMS.dto.request.Create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class CreateShipmentBidRequest {
    private Long transporterId;
    private Double charges;
    private Integer deliveryDays;
    private Double offeredVehicleCapacity;
    private String remarks;
}
