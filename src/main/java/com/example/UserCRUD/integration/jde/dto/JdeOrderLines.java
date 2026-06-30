package com.example.UserCRUD.integration.jde.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class JdeOrderLines {
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
