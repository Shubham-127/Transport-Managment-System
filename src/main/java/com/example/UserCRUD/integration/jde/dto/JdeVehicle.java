package com.example.UserCRUD.integration.jde.dto;

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
    private int MaxWeightCapacity;
    private String totalWeight;
    private int maxVolumeCapacity;
    private String totalVolume;
    private String status;

}
