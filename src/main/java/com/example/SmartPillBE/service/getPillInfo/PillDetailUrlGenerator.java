package com.example.SmartPillBE.service.getPillInfo;
import java.net.URLEncoder;

public class PillDetailUrlGenerator {
    public String generatePillUrl(String pillNumber) {
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
}
