package com.example.UserCRUD.dto.response;

import lombok.*;

// Response INCLUDES orderMasterId so the client can see
// which order this line belongs to when viewing it standalone
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderLinesMasterResponse {

    private Long id;
    private Long orderMasterId;
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
