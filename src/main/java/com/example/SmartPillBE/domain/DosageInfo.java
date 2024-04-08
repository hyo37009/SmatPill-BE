package com.example.SmartPillBE.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
public class DosageInfo {

    @Id @GeneratedValue
    @Column(name = "dosage_id")
    private Long id;

    @OneToMany
    @JoinColumn(name = "pill")
    private List<Pill> pillList;


    private int profile_id; // 관계 설정하기

    private String memo;
    private LocalDateTime prescriptionDate; // 처방 일시
    private String hospital; // 처방 병원
}
