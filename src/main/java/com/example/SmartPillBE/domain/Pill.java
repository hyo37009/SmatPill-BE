package com.example.SmartPillBE.domain;

import com.example.SmartPillBE.domain.doaseRegimen.DosageRegimen;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "pill")
public class Pill {
    @Id
    @Column(name = "pill_number", unique = true, nullable = false)
    private String pillNumber; // 품목일련번호

    private String pillName; // 품목명

    private String dosageForm; // 성상

    private String effect; // 효능효과

    private String category; //일반or전문의약품

    private String imagePath; // 대표 사진 경로

    // == 이하 ai에서 사용하는 컬럼 == //
    private String printFront; // 표시 (앞)
    private String printBack; // 표시 (뒤)
    private String shape; // 의약품 모양
    private String colorFront; // 색깔 (앞)
    private String colorBack; // 색깔 (뒤)
    private String lineFront; // 분할선 (앞)
    private String lineBack; // 분할선 (뒤)
}
