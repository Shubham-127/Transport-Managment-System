package com.example.UserCRUD.model;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shipment_master")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ShipmentMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String shipmentNumber;

    // every order in this shipment MUST belong to this customer
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerMaster customer;

    @Column(nullable = false)
    private String shipmentDate;

    @Column(nullable = false)
    private String status;

    @Column(name = "route_id")
    private Long routeId;// e.g. "CREATED", "DISPATCHED", "DELIVERED"

    @Column
    private String remarks;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "order_ids", columnDefinition = "bigint[]")
    @Builder.Default
    private List<Long> orderIds = new ArrayList<>();

    @OneToMany(mappedBy = "shipment", fetch = FetchType.LAZY)
    @JsonIgnore
    @Builder.Default
    private List<OrderMaster> orders = new ArrayList<>();
}