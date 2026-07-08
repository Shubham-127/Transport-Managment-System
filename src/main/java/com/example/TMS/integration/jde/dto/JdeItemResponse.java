package com.example.TMS.integration.jde.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class JdeItemResponse {
    @JsonProperty("F4101 Count")
    private int count;

    @JsonProperty("DREQ_Item_Master")
    private List<JdeItem> itemMaster;

    private String jde__status;
}
