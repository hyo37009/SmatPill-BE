package com.example.SmartPillBE.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Profile {
    @Id
    @GeneratedValue
    @Column(name = "profile_id")
    private Long id;
    @Column
    private String name;
    @Column
    private String birth;
    @Column
    private Float height;
    @Column
    private Float weight;
    @Column
    private String sex;
    @Column
    private String nickname;
    @Column
    private Boolean represent;


}
