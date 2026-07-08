package com.example.TMS.dto.request.Update;


import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UpdateTransportMasterRequest {

    private String companyName;
    private String gstNumber;
    private String panNumber;
    private String contactPerson;
    private String mobile;
    private String email;
    private String address;
    private String city;
    private String state;
    private String pincode;
    private String active;
    private String remarks;
    private Integer addressNumber;
}