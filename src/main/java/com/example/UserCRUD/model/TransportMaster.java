package com.example.UserCRUD.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "TransportMaster")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TransportMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String gstNumber;

    @Column(nullable = false)
    private String panNumber;

    @Column(nullable = false)
    private String contactPerson;

    @Column(nullable = false)
    private String mobile;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String pincode;

    @Column(nullable = false)
    private String active;

    @Column
    private String remarks;

    @Column(nullable = false)
    private Integer addressNumber;

    // ─── the "ONE" side — many vehicles belong to this one transport ──
    // mappedBy = "transportMaster" tells JPA: "the real foreign key
    // column lives on the VehicleMaster side, in its field named
    // exactly 'transportMaster' — I don't own any column here"
    //
    // cascade = ALL means: deleting a TransportMaster also deletes
    // all its vehicles — makes sense since a vehicle without its
    // owning transport company is meaningless data
    //
    // @JsonIgnore prevents infinite recursion when this entity gets
    // converted to JSON: without it, Transport → Vehicles → Transport
    // → Vehicles ... forever
    @OneToMany(mappedBy = "transportMaster", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<VehicleMaster> vehicles;
}