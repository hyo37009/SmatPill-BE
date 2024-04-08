package com.example.SmartPillBE.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Profile {

    @Id @GeneratedValue
    @Column(name = "profile_id")
    private Long id;

    private String name;
    private String nickname;
    private int age;
    private Gender gender;
    private float height;
    private float weight;
    private boolean isRepresentative;
}
