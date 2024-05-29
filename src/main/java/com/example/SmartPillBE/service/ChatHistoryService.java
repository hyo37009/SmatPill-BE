package com.example.SmartPillBE.service;

import com.example.SmartPillBE.domain.ChatHistory;
import com.example.SmartPillBE.domain.ChatHistoryContent;
import com.example.SmartPillBE.domain.Profile;
import com.example.SmartPillBE.repository.ChatHistoryRepository;
import com.example.SmartPillBE.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatHistoryService {

    private final ChatHistoryRepository chatHistoryRepository;
    private final ProfileRepository profileRepository;

    @Transactional
    public ChatHistory createNewHistory(Profile profile){
        ChatHistory chatHistory = ChatHistory.CreateChatHistory(profile, LocalDateTime.now());
        chatHistoryRepository.save(chatHistory);
        return chatHistory;
    }

    public List<String> getNthChat(Long id, int num){
        ChatHistory chatHistory = chatHistoryRepository.findById(id);
        List<ChatHistoryContent> chatHistoryContents = chatHistory.getChatHistoryContents();

        return chatHistoryContents.stream()
                .map(ChatHistoryContent::getContent)
                .toList();
    }

    public List<ChatHistory> findByProfile(Profile profile){
        return chatHistoryRepository.findByProfile(profile);
    }

    public ChatHistory findById(Long id){
        return chatHistoryRepository.findById(id);
    }


    public List<ChatHistoryContent> getChattingById(Long id) {
        return chatHistoryRepository.findById(id).getChatHistoryContents();
    }
}
