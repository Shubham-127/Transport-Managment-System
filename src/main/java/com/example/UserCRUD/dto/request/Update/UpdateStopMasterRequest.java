package com.example.UserCRUD.dto.request.Update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class UpdateStopMasterRequest {

    private Double distanceFromSource;
    private String stopName;
}
