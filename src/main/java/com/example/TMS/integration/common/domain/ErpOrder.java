package com.example.TMS.integration.common.domain;

import lombok.*;

import java.util.List;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor@Builder
public class ErpOrder {
    private Long orderNumber;
    private String orderType;
    private String company;
    private Long customerNumber;
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
    private List<ErpOrderLine> lines;
}
