package com.example.UserCRUD.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class UpdateItemMasterRequest {

    private String itemNumber;
    private Long shortItemNo;
    private String description;
    private String description2;
    private String thirdItemNumber;
    private String lineType;
    private String stockingType;
}
