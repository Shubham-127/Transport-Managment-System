package com.example.UserCRUD.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "VehicleMaster")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class VehicleMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ─── the "MANY" side — this is the REAL foreign key column ──
    // @ManyToOne means: "many VehicleMaster rows can point to
    // the same one TransportMaster row"
    // @JoinColumn creates an actual column "transport_master_id"
    // inside the vehicle_master table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transport_master_id", nullable = false)
    private TransportMaster transportMaster;

    @Column(nullable = false, unique = true)
    private String vehicleNumber;

    @Column(nullable = false)
    private String vehicleType;

    @Column(nullable = false)
    private Integer maxWeightCapacity;

    @Column(nullable = false)
    private Integer maxVolumeCapacity;

    @Column
    private String gpsDeviceId;

    @Column(nullable = false)
    private String status;

    @Column
    private String totalWeight;

    @Column
    private String totalVolume;
}