package com.example.UserCRUD.integration.jde.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class JdeCustomer {


        private Integer addressNumber;
        private String customerName;
        private String gstNumber;
        private String panNumber;
        private String contactPerson;
        private String mobile;
        private String email;
        private String address;
        private String city;
        private String state;
        private String pinCode;
        private String country;
        private String active;
        private String remarks;

}
