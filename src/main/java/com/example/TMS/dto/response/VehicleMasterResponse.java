package com.example.TMS.dto.response;



import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class VehicleMasterResponse {

    private Long id;
    private Long transportMasterId;   // included so client can see which transport owns this vehicle
    private String vehicleNumber;
    private String vehicleType;
    private Integer maxWeightCapacity;
    private Integer maxVolumeCapacity;
    private String gpsDeviceId;
    private String status;
}