package com.example.UserCRUD.dto.request;

import lombok.*;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor@Builder
public class CreateItemMasterRequest {

    private String itemNumber;
    private Long shortItemNo;
    private String description;
    private String description2;
    private String thirdItemNumber;
    private String lineType;
    private String stockingType;

}
