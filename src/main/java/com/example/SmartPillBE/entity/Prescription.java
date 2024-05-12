package com.example.SmartPillBE.entity;

import jakarta.persistence.*;

@Entity
public class Prescription {
    @Id
    @GeneratedValue
    @Column(name = "prescription_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @Column
    private String path;


}
