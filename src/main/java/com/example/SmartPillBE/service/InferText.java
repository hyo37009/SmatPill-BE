package com.example.SmartPillBE.service;

import com.example.SmartPillBE.ocr.InferTextExtractor;
import com.example.SmartPillBE.repository.PillRepository;

import java.util.List;

public class InferText {

    public static void main(String[] args) {
        // 이미지 파일 경로 설정
        String imagePath = "C:\\CapstoneProject\\SmatPill-BE\\src\\main\\java\\com\\example\\SmartPillBE\\ocr\\디비약봉지.jpg";

        // InferTextExtractor 객체 생성
        InferTextExtractor extractor = new InferTextExtractor();

        // 추출된 inferText 값들을 가져옴
        List<String> inferTexts = extractor.extractInferText(imagePath);

        // 추출된 inferText 값들 출력
        for (String inferText : inferTexts) {
            System.out.println(inferText);
        }

    }

}