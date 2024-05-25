package com.example.SmartPillBE.api;

import com.example.SmartPillBE.domain.ChatHistory;
import com.example.SmartPillBE.domain.ChatHistoryContent;
import com.example.SmartPillBE.domain.Profile;
import com.example.SmartPillBE.repository.ChatHistoryContentRepository;
import com.example.SmartPillBE.service.ChatHistoryService;
import com.example.SmartPillBE.service.ProfileService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatHistoryApiController {
    private final ChatHistoryService chatHistoryService;
    private final ProfileService profileService;
    private final ChatHistoryContentRepository chatHistoryContentRepository;
    @GetMapping("/api/profiles/{id}/chatting")
    public List<ChatResponseDto> getAllByProfile(@PathVariable("id") int id) throws Exception {
        Profile profile = profileService.getProfile(id);
        List<ChatHistory> chatHistories = chatHistoryService.findByProfile(profile);
        return chatHistories.stream()
                .map(c -> new ChatResponseDto(
                        c.getId(),
                        c.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                        c.getChatHistoryContents().stream()
                                .map(cc -> new ChatContentDto(
                                        cc.getId(),
                                        cc.getTimeStamp().format(DateTimeFormatter.ISO_DATE_TIME),
                                        cc.getContent()
                                )).collect(Collectors.toList())
                )).collect(Collectors.toList());
    }

    @Transactional
    @PutMapping("/api/profiles/{id}/chatting")
    public ChatResponse createNewChatHistory(@PathVariable("id") int profileId) throws Exception {
        Profile profile = profileService.getProfile(profileId);
        ChatHistory newHistory = chatHistoryService.createNewHistory(profile);
        return new ChatResponse(newHistory.getId(), "생성되었습니다.");
    }

    @Transactional
    @PutMapping("api/profiles/{id}/chatting/{chatId}")
    public ChatResponse saveNewMessage(@PathVariable("chatId") Long id, @RequestBody String message) {
        ChatHistory chatHistory = chatHistoryService.findById(id);
        ChatHistoryContent content = ChatHistoryContent.createChatHistoryContent(chatHistory, message);
        ChatHistoryContent saved = chatHistoryContentRepository.save(content);
        chatHistory.chatHistoryContents.add(saved);

        return new ChatResponse(id, "정상적으로 저장되었습니다.");
    }

    @GetMapping("api/profiles/{id}/chatting/{chatId}")
    public List<String> getChatting(@PathVariable("chatId") Long id) {
        return chatHistoryService.getChattingById(id);
    }

    @GetMapping("api/profiles/{id}/chatting/{chatId}/{num}")
    public List<String> getNthMessage(@PathVariable("chatId") Long id, @PathVariable("num") int num) {
        return chatHistoryService.getNthChat(id, num);
    }

    @Data
    @AllArgsConstructor
    static class ChatResponseDto {
        Long id;
        String timeStamp;
        List<ChatContentDto> chatContent;
    }

    @Data
    @AllArgsConstructor
    static class ChatContentDto{
        Long id;
        String timeStamp;
        String content;
    }

    @Data
    @AllArgsConstructor
    static class ChatResponse {
        Long id;
        String message;

    }
}
