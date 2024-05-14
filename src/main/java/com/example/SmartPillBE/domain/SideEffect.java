package com.example.SmartPillBE.domain;

import jakarta.persistence.*;

@Entity
public class SideEffect {
    @Id
    @GeneratedValue
    @Column(name = "sideEffect_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @Column
    private String contents;

}
