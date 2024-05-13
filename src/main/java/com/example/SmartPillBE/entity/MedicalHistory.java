package com.example.SmartPillBE.entity;

import jakarta.persistence.*;

@Entity
public class MedicalHistory {
    @Id
    @GeneratedValue
    @Column(name = "medicalHistory_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @Column
    private String disease;


    @Column
    private String diagnosis;


    @Column
    private String memo;

}