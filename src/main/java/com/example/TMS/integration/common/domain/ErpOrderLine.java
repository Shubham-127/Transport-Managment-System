package com.example.TMS.integration.common.domain;

import lombok.*;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor@Builder
public class ErpOrderLine {
    private int lineNumber;
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
