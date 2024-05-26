package com.example.SmartPillBE.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 복약정보
 */
@Entity
@Getter
public class MedicalHistory {
    @Id
    @GeneratedValue
    @Column(name = "medicalHistory_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    private String disease; // 병명

    LocalDate diagnosis; // 진단 날짜

    @Setter
    private String memo;

    public static MedicalHistory createMedicalHistory(Profile profile, String disease,
                                                      LocalDate diagnosis){
        MedicalHistory medicalHistory = new MedicalHistory();
        medicalHistory.profile = profile;
        medicalHistory.disease = disease;
        medicalHistory.diagnosis = diagnosis;
        return medicalHistory;
    }

}
