package com.example.SmartPillBE.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "chat_history")
public class ChatHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "chat_history_id", unique = true)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;


    @Setter
    private String summary = "";

    @OneToMany
    @JoinColumn(name = "chat_history_id")
    public List<ChatHistoryContent> chatHistoryContents = new ArrayList<>();


    public static ChatHistory CreateChatHistory(Profile profile,
                                         LocalDateTime date){
        ChatHistory chatHistory = new ChatHistory();
        chatHistory.profile = profile;
        chatHistory.date = date;
        return chatHistory;
    }

    // == 비즈니스 로직 == //


//    public void saveNewMessage(List<String> messages){
//
//    }
}
