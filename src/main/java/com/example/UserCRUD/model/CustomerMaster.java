package com.example.UserCRUD.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CustomerMaster")
@Getter@Setter@NoArgsConstructor@AllArgsConstructor@Builder
public class CustomerMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private String getNumber;

    @Column(nullable = false)
    private String panNumber;

    @Column(nullable = false)
    private String contactPerson;

    @Column(nullable = false)
    private String mobile;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String pincode;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String active;

    @Column(nullable = false)
    private String remarks;

    @Column(nullable = false)
    private String salesOrder;



}
