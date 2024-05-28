package com.example.SmartPillBE.url;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;


import static org.thymeleaf.util.StringUtils.substring;

public class ExtractHTML {

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

    //HTML따오는 부분
    public List<String> getHTML(String urlToRead){
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        String result = "";
        List<String> resultArr = new ArrayList<>();

        try{
            url = new URL(urlToRead);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            while((line = rd.readLine()) !=null){
                result += line;
                resultArr.add(line.strip());
            }rd.close();
        }catch (Exception e){
            e.printStackTrace();
        }return resultArr;
    }


    //HTML지우고 질문-답
    public List<List<String>> findInfo(List<String> htmlList){

//        List<String> onlyText = new ArrayList<>();
        boolean flag = false;
        boolean iflag = false;
        int flagnum = 0;
        int flagnumber = 0;


        List<String> temp = new ArrayList<>();
        List<List<String>> nextresult = new ArrayList<>();

//        List<String> itemp = new ArrayList<>();


        for(String result : htmlList){
            //HTML없애는 코드
            String convertingText = Jsoup.clean(result, Whitelist.none());

            //자바스크립트 제거
            int count = 0;
            for(int i = 0; i < convertingText.length(); i++){
                char c = convertingText.charAt(i);
                //abcdefghijklmnopqrstvwxyz
                if ( (c >= 'B' && c <= 'L') || (c == 'N') || (c >= 'P' && c <= 'Z') || (c >= 'a' && c <= 'f') || (c >= 'h' && c <= 'l') || (c >= 'n' && c <= 'z') || (c == '*') /*|| (c == '/') */|| (c == '}') ) {
                    count++;
                }
            }
            if (convertingText.isEmpty() || (count != 0 && (convertingText.length() / (double) count) >= 0.95)) {
                continue;
            }

            //e약은요
            if(convertingText.contains("이 약의 효능은 무엇입니까?")) {
                flag = true;
            }
            if(flag && !convertingText.isEmpty()){
                if(convertingText.equals("효능효과")) {
                    return nextresult;
                }
                switch (convertingText){
                    case "이 약의 효능은 무엇입니까?"
                            -> flagnum = 1;
                    case "이 약은 어떻게 사용합니까?"
                            -> {
                        flagnum = 2;
                        nextresult.add(temp);
                        temp = new ArrayList<>();
                    }
                    case "이 약을 사용하기 전에 반드시 알아야 할 내용은 무엇입니까?"
                            -> {
                        flagnum = 3;
                        nextresult.add(temp);
                        temp = new ArrayList<>();
                    }
                    case "이 약의 사용상 주의사항은 무엇입니까?"
                            -> {
                        flagnum = 4;
                        nextresult.add(temp);
                        temp = new ArrayList<>();

                    }
                    case "이 약을 사용하는 동안 주의해야 할 약 또는 음식은 무엇입니까?"
                            -> {
                        flagnum = 5;
                        nextresult.add(temp);
                        temp = new ArrayList<>();
                    }
                    case "이 약은 어떤 이상반응이 나타날 수 있습니까?"
                            -> {
                        flagnum = 6;
                        nextresult.add(temp);
                        temp = new ArrayList<>();
                    }
                    case "이 약은 어떻게 보관해야 합니까?"
                            -> {
                        flagnum = 7;
                        nextresult.add(temp);
                        temp = new ArrayList<>();
                    }
                }
                temp.add(convertingText);
            }

            //e약은요가 없을 때
            if(convertingText.contains("효능효과")){
                iflag = true;
            }
            if(iflag && !convertingText.isEmpty()){
                if(flagnumber == 8){
                    return nextresult;
                }

                if(convertingText.contains("효능효과")){
                    flagnumber = 1;
                }
                if(convertingText.contains("용법용량")){
                    flagnumber = 2;
                    nextresult.add(temp);
                    temp = new ArrayList<>();
                }
                if(convertingText.contains("다음 환자에는 투여하지 말 것.")){
                    flagnumber = 3;
                    nextresult.add(temp);
                    temp = new ArrayList<>();
                }
                if(convertingText.contains("다음 환자에는 신중히 투여할 것.")){
                    flagnumber = 4;
                    nextresult.add(temp);
                    temp = new ArrayList<>();
                }
                if(convertingText.contains("생산실적")){
                    continue;
                }
                if(convertingText.contains("변경이력")){
                    continue;
                }
                if(convertingText.contains("변경일자")){
                    continue;
                }

                if(convertingText.contains("순번")){
                    continue;
                }
                if(convertingText.contains("이상반응")){
                    flagnumber = 5;
                    nextresult.add(temp);
                    temp = new ArrayList<>();
                }
                if(convertingText.contains("부작용")){
                    flagnumber = 6;
                    nextresult.add(temp);
                    temp = new ArrayList<>();
                }
                if(convertingText.contains("일반적 주의")){
                    flagnumber = 7;
                    nextresult.add(temp);
                    temp = new ArrayList<>();
                }
                if(convertingText.contains("상호작용")){
                    flagnumber = 8;
                    nextresult.add(temp);
                    temp = new ArrayList<>();
                }

                temp.add(convertingText);
            }
        }
        return nextresult;
    }

    //리스트 안에 답변 한줄한줄 리스트로 넣는 부분
    public List<List<String>> returnText(String key, List<List<String>> texts) {

        List<List<String>> contents = new ArrayList<>();

        for (int j = 0; j < texts.size(); j++) {
            if (texts.get(j).get(0).contains(key)) {
                contents.add(List.of(key));
                for (int i = 1; i < texts.get(j).size(); i++) {
                    List<String> temp = new ArrayList<>();
                    temp.add(texts.get(j).get(i));
                    contents.add(temp);
                }
            }
        }
        return contents;
    }

}
