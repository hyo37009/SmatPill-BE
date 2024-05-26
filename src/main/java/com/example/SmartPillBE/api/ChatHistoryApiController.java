package com.example.SmartPillBE.api;

import com.example.SmartPillBE.domain.ChatHistory;
import com.example.SmartPillBE.domain.ChatHistoryContent;
import com.example.SmartPillBE.domain.Profile;
import com.example.SmartPillBE.service.ChatHistoryContentService;
import com.example.SmartPillBE.service.ChatHistoryService;
import com.example.SmartPillBE.service.ChatbotService;
import com.example.SmartPillBE.service.ProfileService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatHistoryApiController {
    private final ChatHistoryService chatHistoryService;
    private final ProfileService profileService;
    private final ChatHistoryContentService chatHistoryContentService;
    private final ChatbotService chatbot;

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
    public GeneralResponse createNewChatHistory(@PathVariable("id") int profileId) throws Exception {
        Profile profile = profileService.getProfile(profileId);
        ChatHistory newHistory = chatHistoryService.createNewHistory(profile);
        return new GeneralResponse(newHistory.getId(), "생성되었습니다.");
    }

    @Transactional
    @PostMapping("/api/profiles/{id}/chatting/{chatId}")
    public String saveNewMessage(@PathVariable("chatId") Long id, @RequestBody String message) throws IOException {
        ChatHistory chatHistory = chatHistoryService.findById(id);
        chatHistoryContentService.createContent(chatHistory, message);

        List<String> history = chatHistory.getChatHistoryContents()
                .stream()
                .map(ChatHistoryContent::getContent)
                .collect(Collectors.toList());


        String result = getResult(message, history);
        chatHistoryContentService.createContent(chatHistory, result);
        return result;
    }

    private String getResult(String message, List<String> history) throws IOException {
        URL url = new URL("http://15.165.129.252:5000/api/ai");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setUseCaches(false);
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; charset=utf-8");

        JSONObject json = new JSONObject();
        json.put("message", message);
        json.put("history", history);

        System.out.println("json = " + json);

//        OutputStream os = con.getOutputStream();

        String body = json.toString();

//        os.write(body.getBytes("euc-kr"));

        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.write(body.getBytes("utf-8"));
        wr.flush();
        wr.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }
        br.close();

        String korResponse = uniToKor(response.toString());

//            return korResponse.substring(16, korResponse.length() - 3);


//        chatHistoryContentService.createContent(chatHistory, chatted);
        return korResponse.substring(16, korResponse.length() - 3);
    }

    @GetMapping("/api/profiles/{id}/chatting/{chatId}")
    public List<String> getChatting(@PathVariable("chatId") Long id) {
        return chatHistoryService.getChattingById(id);
    }

    @GetMapping("/api/profiles/{id}/chatting/{chatId}/{num}")
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
    static class ChatContentDto {
        Long id;
        String timeStamp;
        String content;
    }

    @Data
    @AllArgsConstructor
    static class ChatDto {
        String message;
        List<String> history;
    }

    public String uniToKor(String uni) {
        StringBuffer result = new StringBuffer();

        for (int i = 0; i < uni.length(); i++) {
            if (uni.charAt(i) == '\\' && uni.charAt(i + 1) == 'u') {
                Character c = (char) Integer.parseInt(uni.substring(i + 2, i + 6), 16);
                result.append(c);
                i += 5;
            } else {
                result.append(uni.charAt(i));
            }
        }
        return result.toString();
    }

}
