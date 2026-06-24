package com.example.UserCRUD.dto.response;

import lombok.*;
import java.util.List;

// The top-level response for the new endpoint —
// has all the same fields as CustomerMasterResponse, PLUS a
// nested list of orders (which themselves nest their lines)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CustomerFullDetailsResponse {

    private Long id;
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

    // NEW — the nested list of orders, each carrying its own lines
    private List<OrderWithLinesResponse> orders;
}