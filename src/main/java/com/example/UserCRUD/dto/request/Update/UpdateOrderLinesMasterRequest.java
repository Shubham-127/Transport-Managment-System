package com.example.UserCRUD.dto.request.Update;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UpdateOrderLinesMasterRequest {

    private Integer lineNumber;
    private String itemNumber;
    private String description;
    private Integer quantity;
    private String uom;
    private Double unitPrice;
    private Double lineAmount;
    private String unitWeight;
    private String weightUnit;
    private String unitVolume;
    private String volumeUnit;
}