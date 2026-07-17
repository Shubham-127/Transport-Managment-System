package com.example.TMS.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;



import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "shipment_bid")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ShipmentBid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipment_id", nullable = false)
    private ShipmentMaster shipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transport_id", nullable = false)
    private TransportMaster transport;

    @Column(nullable = false)
    private Double charges; // transporter's quoted price

    @Column(nullable = false)
    private Integer deliveryDays; // transporter's promised delivery time

    @Column
    private Double offeredVehicleCapacity; // transporter's vehicle capacity for this bid

    @Column
    private String remarks;

    @Column(nullable = false)
    private String status; // PENDING, ACCEPTED, REJECTED

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

