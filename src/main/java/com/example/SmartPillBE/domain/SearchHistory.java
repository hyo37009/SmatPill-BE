package com.example.SmartPillBE.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class SearchHistory {
    @Id @GeneratedValue
    @Column(name = "search_history_id")
    private Long id;

    @OneToOne
    private Pill pill;

    private LocalDateTime searchDate;

}
