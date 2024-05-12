package com.example.SmartPillBE.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
public class ChatHistory {
    @Id
    @GeneratedValue
    @Column(name = "chatHistory_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Column
    private String summary;

    @Column
    private Integer turn;

    @Column
    private String sender;

    @Column
    private String contents;

}
