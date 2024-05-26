package com.example.SmartPillBE.service;

import com.example.SmartPillBE.domain.ChatHistory;
import com.example.SmartPillBE.domain.ChatHistoryContent;
import com.example.SmartPillBE.repository.ChatHistoryContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatHistoryContentService {
    private final ChatHistoryContentRepository contentRepository;

    public Long createContent(ChatHistory chatHistory, String content){
        ChatHistoryContent chatHistoryContent = ChatHistoryContent.createChatHistoryContent(chatHistory, content);
        contentRepository.save(chatHistoryContent);
        return chatHistoryContent.getId();
    }

    public List<String> getAllChat(ChatHistory chatHistory){
        List<ChatHistoryContent> contents = contentRepository.findByChatHistory(chatHistory);
        return contents.stream()
                .map(ChatHistoryContent::getContent)
                .toList();
    }
}
