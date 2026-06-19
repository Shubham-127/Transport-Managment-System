package com.example.UserCRUD.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ItemMaster")
@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class ItemMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
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
