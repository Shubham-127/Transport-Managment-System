package com.example.UserCRUD.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "StopMaster")@Getter@Setter@NoArgsConstructor@AllArgsConstructor@Builder
public class StopMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_master_id", nullable = false)
    private RouteMaster routeMaster;

    @Column(nullable = false)
    private Double distanceFromSource;

    @Column(nullable = false)
    private String stopName;


}
