package com.example.UserCRUD.dto.response;

import lombok.*;

import java.util.List;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor@Builder
public class CustomerOrderWithRouteResponse {

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
    private List<RouteWithOrderResponse> route;

}
