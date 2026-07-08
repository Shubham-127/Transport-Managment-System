package com.example.TMS.dto.request.Create;

import lombok.*;

// Notice: NO transportMasterId field here!
// The transport's id comes from the URL path, not the request body —
// same pattern as CreateOrderLinesMasterRequest
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CreateVehicleMasterRequest {

    private String vehicleNumber;
    private String vehicleType;
    private Integer maxWeightCapacity;
    private Integer maxVolumeCapacity;
    private String gpsDeviceId;
    private String status;
}
