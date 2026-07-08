package com.example.TMS.dto.response;

import lombok.*;
import java.util.List;

// This is a special response shape just for the nested view —
// it has all the same fields as OrderMasterResponse, PLUS a
// nested list of lines
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderWithLinesResponse {

    private Long id;
    private String orderNumber;
    private String orderType;
    private String company;
    private String customerNumber;
    private String orderDate;
    private String requestedDate;
    private String branchPlant;
    private String shipToCustomerNumber;
    private String shipToCustomerName;
    private String shipToAddress1;
    private String shipToCity;
    private String shipToState;
    private String shipToPinCode;
    private String unitWeight;
    private String weightUnit;
    private String totalVolume;
    private String volumeUnit;
    private String status;
    private String currencyCode;

    // NEW — the nested list of lines belonging to this order
    private List<OrderLinesMasterResponse> lines;
}