package com.example.UserCRUD.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "CustomerMaster")
@Getter@Setter@NoArgsConstructor@AllArgsConstructor@Builder
public class CustomerMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
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

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<OrderMaster> orders;



}
