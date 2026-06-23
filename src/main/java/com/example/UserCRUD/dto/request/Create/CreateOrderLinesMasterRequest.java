package com.example.UserCRUD.dto.request.Create;

import lombok.*;

// Notice: NO orderMasterId field here!
// The order's id comes from the URL path (/api/order-lines/{orderId})
// not from the request body — this prevents a client from accidentally
// attaching a line to the wrong order by sending a mismatched id
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CreateOrderLinesMasterRequest {

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
