package com.example.SmartPillBE.entity;

import jakarta.persistence.*;

@Entity
public class SideEffect {
    @Id
    @GeneratedValue
    @Column(name = "sideEffect_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @Column
    private String contents;

}
