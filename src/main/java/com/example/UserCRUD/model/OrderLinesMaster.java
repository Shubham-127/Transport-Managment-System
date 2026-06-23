package com.example.UserCRUD.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "OrderLineMaster")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderLinesMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_master_id",nullable = false)
    private OrderMaster orderMaster;

    @Column(nullable = false)
    private Integer lineNumber;

    @Column(nullable = false)
    private String itemNumber;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private String uom;

    @Column(nullable = false)
    private Double unitPrice;

    @Column(nullable = false)
    private Double lineAmount;

    @Column
    private String unitWeight;

    @Column
    private String weightUnit;

    @Column
    private String unitVolume;

    @Column
    private String volumeUnit;
}
