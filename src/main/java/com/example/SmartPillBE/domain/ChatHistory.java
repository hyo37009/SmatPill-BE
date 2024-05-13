package com.example.SmartPillBE.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Getter
@Table(name = "chat_history")
public class ChatHistory {
    @Id
    @GeneratedValue
    @Column(name = "chatHistory_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private String summary;

    private int turn;

    private String sender;

    private String contents;

}
