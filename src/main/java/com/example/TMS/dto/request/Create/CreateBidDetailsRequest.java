package com.example.TMS.dto.request.Create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class CreateBidDetailsRequest {
    private Double expectedAmount;
    private String expectedDeliveryDays;
    private Double vehicleCapacity;
    private String remarks;
}
