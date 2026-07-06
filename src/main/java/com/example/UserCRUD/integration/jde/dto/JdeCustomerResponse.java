package com.example.UserCRUD.integration.jde.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class JdeCustomerResponse {

    @JsonProperty("V560999P Count")
    private int count;

    @JsonProperty("DREQ_Customer_Data")
    private List<JdeCustomer> customers;

    private String jde__status;
}