package com.example.UserCRUD.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
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
