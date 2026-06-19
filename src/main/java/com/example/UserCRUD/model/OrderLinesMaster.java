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

    // This is the FOREIGN KEY — it stores the id of the OrderMaster
    // that this line belongs to. We use a plain Long here (not a
    // @ManyToOne relationship) to keep things simple and explicit —
    // every line just remembers which order's id it belongs to.
    @Column(nullable = false)
    private Long orderMasterId;

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
