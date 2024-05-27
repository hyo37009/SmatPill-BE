package com.example.SmartPillBE.url;

import com.example.SmartPillBE.service.PillDetailUrlGenerator;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExtractHTMLTest {
    @Test
    void extractHTML() {

//        String pillNumber = "202200658"; //우먼스타이레놀
        String pillNumber = "197900277";//게보린
//        String pillNumber = "196000011"; //페니라민정
//        String pillNumber = "198200403";
//        String pillNumber = "197900575";
//        String pillNumber = "199000707"; //메녹틸정

        PillDetailUrlGenerator pilldetail = new PillDetailUrlGenerator();
        String url = pilldetail.generatePillUrl(pillNumber);

        System.out.println("url = " + url);

        ExtractHTML extractHTML = new ExtractHTML();
        List<String> html = extractHTML.getHTML(url);
        List<List<String>> onlyText = extractHTML.findInfo(html);


        System.out.println("=============== S 시작 ================");
        for (String s : html) {
            System.out.println("s = " + s);
        }
        System.out.println("=============== S 끝 ================");

        System.out.println("=============== T 시작 ================");
        for (List<String> strings : onlyText) {
            System.out.println("====");
            for (String string : strings) {
                System.out.println("t = " + string);
            }
        }
        System.out.println("=============== T 끝 ================");



        List<String> keyList = new ArrayList<>(Arrays.asList("이 약의 효능은 무엇입니까?", "이 약은 어떻게 사용합니까?", "이 약을 사용하기 전에 반드시 알아야 할 내용은 무엇입니까?", "이 약의 사용상 주의사항은 무엇입니까?", "이 약을 사용하는 동안 주의해야 할 약 또는 음식은 무엇입니까?", "이 약은 어떤 이상반응이 나타날 수 있습니까?"));

        List<String> keyy = new ArrayList<>(Arrays.asList("효능효과", "용법용량", "다음 환자에는 투여하지 말 것.", "다음 환자에는 신중히 투여할 것.", "이상반응", "부작용", "일반적 주의", "상호작용"));

        for (String key : keyList) {
            List<List<String>> extractedText = extractHTML.returnText(key, onlyText);
            if (!extractedText.isEmpty()) {
//                System.out.println("=== " + key + " ===");
                System.out.println(extractedText);
            }
        }

        for (String keys : keyy) {
            List<List<String>> extractedText = extractHTML.returnText(keys, onlyText);
            if (!extractedText.isEmpty()) {
//                System.out.println("=== " + keys + " ===");
                System.out.println(extractedText);
            }
        }


/*
//        String key = "내용";//리스트
        for(String key : keyList){
//            List<String> extractedText = extractHTML.returnText(key, onlyText);
            System.out.println(extractHTML.returnText(key, onlyText));
        }
        for(String keys : keyy){
//            List<String> extractedText = extractHTML.returnText(key, onlyText);
            System.out.println(extractHTML.returnText(keys, onlyText));
        }
//        System.out.println("효능~~~~~" + extractHTML.returnText(key, onlyText));//for key

//        for(String key : keyy){
////            List<String> extractedText = extractHTML.returnText(key, onlyText);
//            System.out.println(extractHTML.returnText(key, onlyText));
//        }
        //List<String> text = extractHTML.findInfo(onlyText);*/
    }

}