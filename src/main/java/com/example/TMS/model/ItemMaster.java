package com.example.TMS.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ItemMaster")
@Getter@Setter@NoArgsConstructor@AllArgsConstructor@Builder
public class ItemMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String itemNumber;

    @Column(nullable = false)
    private Long shortItemNo;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String description2;

    @Column(nullable = false)
    private String thirdItemNumber;

    @Column(nullable = false)
    private String lineType;

    @Column(nullable = false)
    private String stockingType;
}
