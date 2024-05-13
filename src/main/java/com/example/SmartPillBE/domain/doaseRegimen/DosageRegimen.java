package com.example.SmartPillBE.domain.doaseRegimen;

import jakarta.persistence.Embeddable;

@Embeddable
public class DosageRegimen {
    // 1일
    private int frequency; // 3회
    private double perIntake; // 2.5 알
}
