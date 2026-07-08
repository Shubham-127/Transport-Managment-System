package com.example.TMS.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="shipment_bid")
@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class ShipmentBid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double bidAmount;

    @Column(nullable = false)
    private String estimatedDelivery;

    @Column
    private String remarks;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String bidDate;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "Shipment_id", nullable = false)
    private ShipmentMaster shipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = " transport_id", nullable = false)
    private TransportMaster transport;


}
