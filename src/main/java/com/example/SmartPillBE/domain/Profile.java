package com.example.SmartPillBE.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * 프로필
 */
@Entity
@Getter
@Table(name = "profile")
public class Profile {

    @Id
    @GeneratedValue
    @Column(name = "profile_id")
    private int id;

    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;

    private float height;
    private float weight;

    @Enumerated(EnumType.STRING)
    private Gender sex;

    private String nickname;
    private boolean isRepresentative;

    private int age;

}
