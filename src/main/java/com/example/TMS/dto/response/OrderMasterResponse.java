package com.example.TMS.dto.response;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderMasterResponse {

    private Long id;
    private Long orderNumber;
    private String orderType;
    private String company;
    private String customerNumber;
    private String orderDate;
    private String requestedDate;
    private String branchPlant;
    private Long shipToCustomerNumber;
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
}
