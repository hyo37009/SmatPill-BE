package com.example.SmartPillBE.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatbotService {
    //    private final WebClient webClient;
//

    public String chat(String message, List<String> history){
        WebClient webClient = WebClient.create("http://15.165.129.252:5000/api/ai");
        String url = "/api/ai";
//        System.out.println(BodyInserters.fromFormData("message", message)
//                        .with("history", history.stream()
//                                .map( i -> "\"" + i + "\"")
//                                .toList().toString()).toString());
//        return null;
        requestBody r = new requestBody(message, history);
        System.out.println("requestBody = " + r);

        String block = webClient
                .post()
                .uri("")
                .bodyValue(r)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println(block);

        return null;
    }

    @Data
    @AllArgsConstructor
    static class requestBody{
        String message;

        public requestBody(String message, List<String> history) {
            this.message = message;
            this.history = history.stream()
                    .map(h ->
                            "\"" + h + "\"")
                    .toList().toString();
        }

        String history;
    }


}
