package com.example.UserCRUD.dto.request.Create;

import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateShipmentMasterRequest {
    private String shipmentNumber;
    private Long customerId; // the CustomerMaster.id (PK), not the business customerId code
    private String shipmentDate;
    private String status;
    private String remarks;

    // the order IDs (OrderMaster.id) to include in this shipment —
    // all must belong to the same customer above
    private List<Long> orderIds;
}
