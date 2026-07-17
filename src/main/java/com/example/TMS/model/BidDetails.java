package com.example.TMS.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
@Entity
@Table(name = "bidDetails")
@Getter@Setter@NoArgsConstructor@AllArgsConstructor@Builder
public class BidDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipment_id", nullable = false, unique = true)
    private ShipmentMaster shipment;

    @Column(nullable = false)
    private Double expectedAmount;

    @Column(nullable = false)
    private String expectedDeliveryDays;

    @Column(nullable = false)
    private Double vehicleCapacity;

    @Column
    private String remarks;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @CreationTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;



}
