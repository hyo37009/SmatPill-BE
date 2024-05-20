package com.example.SmartPillBE.domain;

import jakarta.persistence.*;
import lombok.Setter;

@Entity
public class SideEffect {
    @Id
    @GeneratedValue
    @Column(name = "sideEffect_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="pill_id")
    private Pill pill;

    @Setter
    @Column
    private String contents;



}
