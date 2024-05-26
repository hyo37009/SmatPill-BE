package com.example.SmartPillBE.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChatbotServiceTest {
    @Autowired
    private ChatbotService chatbotService;
    @Test
    public void chat() throws Exception {
        // given
        chatbotService.chat("ㅎㅇ", new ArrayList<>());
        // when

        // then

    }

}