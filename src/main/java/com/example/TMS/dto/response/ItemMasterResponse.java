package com.example.TMS.dto.response;

import lombok.*;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor@Builder
public class ItemMasterResponse {
    private Long id;
    private String itemNumber;
    private Long shortItemNo;
    private String description;
    private String description2;
    private String thirdItemNumber;
    private String lineType;
    private String stockingType;
}
