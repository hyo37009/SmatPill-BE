package com.example.SmartPillBE.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Getter
public class ChatHistoryContent {
    @Id
    @GeneratedValue
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_history_id")
    private ChatHistory chatHistory;

    @DateTimeFormat(pattern = "yy-MM-dd HH:mm:ss")
    LocalDateTime timeStamp;


    private String content;


    public static ChatHistoryContent createChatHistoryContent(ChatHistory chatHistory, String content){
        ChatHistoryContent chatHistoryContent = new ChatHistoryContent();
        chatHistoryContent.content = content;
        chatHistoryContent.chatHistory = chatHistory;
        chatHistoryContent.timeStamp = LocalDateTime.now();
        return chatHistoryContent;
    }
}
