package com.example.UserCRUD.integration.jde.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class JdeOrderResponse {
    private int count;
    private List<JdeOrder> orders;
    private String jde__status;
}
