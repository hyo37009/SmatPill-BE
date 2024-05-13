package com.example.SmartPillBE.domain;

import jakarta.persistence.*;
import lombok.Getter;

/**
 * 복약정보
 */
@Entity
@Getter
@Table(name = "medical_history")
public class MedicalHistory {
    @Id
    @GeneratedValue
    @Column(name = "medicalHistory_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    private String disease;


    private String diagnosis;


    private String memo;

}
