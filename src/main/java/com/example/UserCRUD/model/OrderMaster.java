package com.example.UserCRUD.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id",nullable = false)
    private CustomerMaster customer;

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

    @OneToMany(mappedBy = "orderMaster",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    @Builder.Default
    private List<OrderLinesMaster> Lines = new ArrayList<>();


}
