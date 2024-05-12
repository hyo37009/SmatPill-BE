package com.example.SmartPillBE.entity;

import jakarta.persistence.*;

@Entity
public class Bookmark {
    @Id
    @GeneratedValue
    @Column(name = "bookmark_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @OneToOne
    @Column
    private Pill pillNumber;

    @Column
    private String memo;

}
