package com.example.SmartPillBE.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Pill {
    @Id
    @GeneratedValue
    @Column(name = "pill_pillNumber")
    private String pillNumber;

    @Column
    private String pillName;

    @Column
    private String dosageForm;

    @Column
    private String effect;

    @Column
    private String medicationInfo;

    @Column
    private String dosageRegimen;

    @Column
    private String category;

}
