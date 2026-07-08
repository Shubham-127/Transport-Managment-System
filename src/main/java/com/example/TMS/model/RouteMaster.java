package com.example.TMS.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "RouteMaster")
@Getter@Setter@NoArgsConstructor@AllArgsConstructor@Builder
public class RouteMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String routeCode;

    @Column(nullable = false)
    private String sourceLocation;

    @Column(nullable = false)
    private String destinationLocation;

    @Column(nullable = false)
    private Double distance;

    @Column(nullable = false)
    private String estimatedTime;

    @OneToMany(mappedBy = "routeMaster", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Builder.Default                     // ← prevents null on builder construction
    private List<StopMaster> stops = new ArrayList<>();




}
