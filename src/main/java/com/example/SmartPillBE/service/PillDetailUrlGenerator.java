package com.example.SmartPillBE.service;
import java.net.URLEncoder;

public class PillDetailUrlGenerator {
    public String generatePillDetailUrl(String pillNumber) {
        try {
            String baseUrl = "https://nedrug.mfds.go.kr/pbp/CCBBB01/getItemDetailCache?cacheSeq=";
            String updateTs = "updateTs2024-04-14%2023:12:48.0b";

            // 약 번호를 URL 인코딩하여 URL에 추가
            String encodedPillNumber = URLEncoder.encode(pillNumber, "UTF-8");
            String fullUrl = baseUrl + encodedPillNumber + updateTs;

            return fullUrl;
        } catch (Exception e) {
            System.out.println("Error generating pill detail URL: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        PillDetailUrlGenerator urlGenerator = new PillDetailUrlGenerator();
        String pillNumber = "200202893"; // 예시 약 번호

        String detailUrl = urlGenerator.generatePillDetailUrl(pillNumber);
        if (detailUrl != null) {
            System.out.println("Generated Pill Detail URL: " + detailUrl);
        } else {
            System.out.println("Failed to generate Pill Detail URL.");
        }
    }
}
