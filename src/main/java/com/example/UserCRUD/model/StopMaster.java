package com.example.UserCRUD.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "StopMaster")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StopMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    private RouteMaster routeMaster;

    @Column(nullable = false)
    private Integer stopSequence;

    @Column(nullable = false)
    private String stopLocation;

    @Column(nullable = false)
    private Double distanceFromPrevious;

    @Column(nullable = false)
    private String estimatedTimeFromPrevious;
}