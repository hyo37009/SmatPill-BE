package com.example.SmartPillBE.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class SideEffect {

    @Id
    @OneToOne
    @JoinColumn(name = "pill", unique = true)
    private Pill pill;

    private LocalDateTime writeDate;
    private LocalDateTime sideEffectDate;
    private String memo;
}
