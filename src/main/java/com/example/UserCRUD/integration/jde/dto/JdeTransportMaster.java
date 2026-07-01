package com.example.UserCRUD.integration.jde.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter@Setter@NoArgsConstructor@JsonIgnoreProperties(ignoreUnknown = true)
public class JdeTransportMaster {
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
    private List<JdeVehicle> vehicles;
}
