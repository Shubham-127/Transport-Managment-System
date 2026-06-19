package com.example.UserCRUD.dto.request;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UpdateOrderMasterRequest {

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
    private String unitVolume;
    private String volumeUnit;
    private String status;
    private String currencyCode;
}
