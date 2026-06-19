package com.example.UserCRUD.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "OrderMaster")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long orderNumber;

    @Column(nullable = false)
    private String orderType;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    private String customerNumber;

    @Column(nullable = false)
    private String orderDate;

    @Column(nullable = false)
    private String requestedDate;

    @Column(nullable = false)
    private String branchPlant;

    @Column(nullable = false)
    private Long shipToCustomerNumber;

    @Column(nullable = false)
    private String shipToCustomerName;

    @Column(nullable = false)
    private String shipToAddress1;

    @Column(nullable = false)
    private String shipToCity;
    @Column(nullable = false)
    private String shipToPinCode;
    @Column(nullable = false)
    private String shipToState;
    @Column(nullable = false)
    private String unitWeight;
    @Column(nullable = false)
    private String weightUnit;
    @Column(nullable = false)
    private String unitVolume;
    @Column(nullable = false)
    private String volumeUnit;
    @Column(nullable = false)
    private String status;
    @Column(nullable = false)
    private String currencyCode;


}
