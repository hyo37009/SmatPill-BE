package com.example.SmartPillBE.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
public class MedInfo {
    @Id
    @GeneratedValue
    @Column(name = "medInfo_id")
    private Long id;

    @Column
    private String pillNumber;

    @Column
    private Integer howLong;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Column
    private String bigTime;

    @Column
    private String time;

    @Column
    private Boolean alarm;

}
