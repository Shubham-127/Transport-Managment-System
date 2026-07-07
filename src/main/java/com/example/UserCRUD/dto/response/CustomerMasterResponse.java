package com.example.UserCRUD.dto.response;

import lombok.*;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor@Builder
public class CustomerMasterResponse {
    private Long id;
    private Long customerId;
    private String customerName;
    private String gstNumber;
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
