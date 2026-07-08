package com.example.TMS.integration.jde.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class JdeItem {
    private String itemNumber;
    private Long shortItemNo;
    private String description;
    private String description2;
    private String thirdItemNumber;
    private String lineType;
    private String stockingType;

}
