package com.example.SmartPillBE.repository;

import com.example.SmartPillBE.domain.ChatHistory;
import com.example.SmartPillBE.domain.ChatHistoryContent;
import com.example.SmartPillBE.domain.Profile;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Repository
@Transactional(readOnly = true)
public class ChatHistoryContentRepository {
    private final EntityManager em;

    public ChatHistoryContent save(ChatHistoryContent chatHistoryContent){
        em.persist(chatHistoryContent);
        return chatHistoryContent;
    }

    public List<ChatHistoryContent> findByChatHistory(ChatHistory chatHistory) {
        return em.createQuery("select c from ChatHistoryContent c " +
                        "where c.chatHistory = :chatHistory " +
                        "order by c.timeStamp", ChatHistoryContent.class)
                .setParameter("chatHistory", chatHistory)
                .getResultList();
    }



}
