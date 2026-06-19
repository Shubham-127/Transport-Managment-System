package com.example.UserCRUD.dto.request;

import lombok.*;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor@Builder
public class CreateCustomerMasterRequest {
    private Long customerId;
    private String customerName;
    private String getNumber;
    private String panNumber;
    private String contactPerson;
    private String mobile;
    private String email;
    private String address;
    private String city;
    private String state;
    private String pincode;
    private String country;
    private String active;
    private String remarks;
    private String salesOrder;

}
