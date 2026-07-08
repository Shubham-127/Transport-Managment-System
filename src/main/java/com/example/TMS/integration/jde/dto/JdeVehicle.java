package com.example.TMS.integration.jde.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class JdeVehicle {
    private String vehicleNumber;
    private String VehicleType;
    private Integer MaxWeightCapacity;
    private String totalWeight;
    private Integer maxVolumeCapacity;
    private String totalVolume;
    private String status;
    private String gpsDeviceId;

}
