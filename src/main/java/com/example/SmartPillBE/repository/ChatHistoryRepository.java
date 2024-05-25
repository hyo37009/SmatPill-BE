package com.example.SmartPillBE.repository;

import com.example.SmartPillBE.domain.ChatHistory;
import com.example.SmartPillBE.domain.ChatHistoryContent;
import com.example.SmartPillBE.domain.Profile;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatHistoryRepository {
    private final EntityManager em;

    public List<ChatHistory> findByProfile(Profile profile){
        return em.createQuery("select c from ChatHistory c where c.profile = :profile", ChatHistory.class)
                .setParameter("profile", profile)
                .getResultList();
    }

    public ChatHistory findById(Long id){
        return em.find(ChatHistory.class, id);
    }

    public void save(ChatHistory chatHistory){
        em.persist(chatHistory);
    }

    public void delete(ChatHistory chatHistory){
        em.remove(chatHistory);
    }

}
