package com.example.SmartPillBE.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Pill {

    @Id
    @Column(name = "pill_id")
    private Long id;

    private String nameKR;
    private String nameEN;
    private String company;
    private String category; // 추후 Enum 형식으로 변환
    private String imageLink;

}
