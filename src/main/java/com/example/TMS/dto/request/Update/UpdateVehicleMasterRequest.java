package com.example.TMS.dto.request.Update;



import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UpdateVehicleMasterRequest {

    private String vehicleNumber;
    private String vehicleType;
    private Integer maxWeightCapacity;
    private Integer maxVolumeCapacity;
    private String gpsDeviceId;
    private String status;
}

