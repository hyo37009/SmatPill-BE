package com.example.SmartPillBE.url;

import com.example.SmartPillBE.service.PillDetailUrlGenerator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;


import static org.apache.catalina.security.SecurityUtil.remove;
import static org.thymeleaf.util.StringUtils.substring;

public class ExtractHTML {

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
           // System.out.println(convertingText);

            //자바스크립트 제거
            int count = 0;
            for(int i = 0; i < convertingText.length(); i++){
                char c = convertingText.charAt(i);
//                if ( (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '8' && c <= '9') || (c == '*') || (c == '/') || (c == '}') ) {
//                    count++;
//                }
                //abcdefghijklmnopqrstvwxyz
                if ( (c >= 'B' && c <= 'L') || (c == 'N') || (c >= 'P' && c <= 'Z') || (c >= 'a' && c <= 'f') || (c >= 'h' && c <= 'l') || (c >= 'n' && c <= 'z') || (c == '*') /*|| (c == '/') */|| (c == '}') ) {
                    count++;
                }
            }
            if (convertingText.isEmpty() || (count != 0 && (convertingText.length() / (double) count) >= 0.95)) {
                continue;
            }
            System.out.println(convertingText);
            /*if (convertingText.isEmpty() || (convertingText.length() / (double) count) >= 0.9) {
                continue;
            }*/

            //e약은요
            if(convertingText.contains("이 약의 효능은 무엇입니까?")) {
                flag = true;
            }
            if(flag && !convertingText.isEmpty()){
                if(convertingText.equals("효능효과")) {
                    return nextresult;
                }
//                onlyText.add(convertingText);
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
//                if(convertingText.equals("재심사, RMP, 보험, 기타정보")){
//                    return nextresult;
//                }
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

                /*switch(convertingText){
                    case "효능효과"
                            -> flagnumber = 1;

                    case "용법용량"
                            -> {
                        flagnumber = 2;
                        nextresult.add(temp);
                        temp = new ArrayList<>();
                    }

                    case "1. 다음 환자에는 투여하지 말 것."
                            -> {
                        flagnumber = 3;
                        nextresult.add(temp);
                        temp = new ArrayList<>();
                    }
                    case "2. 다음 환자에는 투여하지 말 것."
                            -> {
                        flagnumber = 3;
                        nextresult.add(temp);
                        temp = new ArrayList<>();
                    }

                    case "2. 다음 환자에는 신중히 투여할 것."
                            -> {
                        flagnumber = 4;
                        nextresult.add(temp);
                        temp = new ArrayList<>();
                    }
                    case "3. 다음 환자에는 신중히 투여할 것."
                            -> {
                        flagnumber = 4;
                        nextresult.add(temp);
                        temp = new ArrayList<>();
                    }

                    case "4. 이상반응"
                            -> {
                        flagnumber = 5;
                        nextresult.add(temp);
                        temp = new ArrayList<>();
                    }
                    case "5. 이상반응"
                            -> {
                        flagnumber = 5;
                        nextresult.add(temp);
                        temp = new ArrayList<>();
                    }

                    case "3. 부작용"
                            -> {
                        flagnumber = 6;
                        nextresult.add(temp);
                        temp = new ArrayList<>();
                    }

                    case "일반적 주의"
                            -> {
                        flagnumber = 7;
                        nextresult.add(temp);
                        temp = new ArrayList<>();
                    }

                }*/
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
                for (int i = 1; i < texts.get(j).size(); i++) {
                    List<String> temp = new ArrayList<>();
                    temp.add(texts.get(j).get(i));
                    contents.add(temp);
                }
            }
        }
        return contents;


//        List<List<String>> contents = new ArrayList<>();
//
//        for (int j = 0; j < texts.size(); j++) {
//            if (texts.get(j).get(0).contains(key)) {
//                List<String> temp = new ArrayList<>();
//                for (int i = 1; i < texts.get(j).size(); i++) {
//
//                    temp.add(texts.get(j).get(i));
//                    //contents.add(texts.get(j).get(i));
//                    //System.out.println(texts.get(j).get(i));
//                }
//                contents.add(temp);
//            }
//        }
//
//        return contents;


        /*효능
           사용
        내용
                주의사항
        주의할 약 또는 음식
        이상반응*/
        //첫번째 줄에 효능이라는 단어가 포함된다면
        //사용자가 사용을 입력하면 이 약은 어떻게 사용합니까?의 내용 출력

        //int flagkey = 0;

        /*case "이 약의 효능은 무엇입니까?"
                -> flagnum = 1;
        case "이 약은 어떻게 사용합니까?"
                -> {
            flagnum = 2;
            nextresult.add(temp);
            temp = new ArrayList<>();
        }*/

        /*switch (keyword){
            case "효능"
                ->{
                flagkey = 1;
                if(texts.get(0).get(0).contains("효능")) {
                    for (int i = 1; i < texts.get(0).size(); i++) {
                        System.out.println(texts.get(0).get(i));
                    }
                }
            }
            case "사용"
                ->{
                flagkey = 2;
                if(texts.get(1).get(0).contains("사용")){
                    for(int i = 1; i < texts.get(1).size(); i++){
                        System.out.println(texts.get(1).get(i));
                    }
                }
            }
            case "내용"
                ->{
                flagkey = 3;
                if(texts.get(2).get(0).contains("내용")){
                    for(int i = 1; i < texts.get(2).size(); i++){
                        System.out.println(texts.get(2).get(i));
                    }
                }
            }
            case "주의사항"
                ->{
                flagkey = 4;
                if(texts.get(3).get(0).contains("주의사항")){
                    for(int i = 1; i < texts.get(3).size(); i++){
                        System.out.println(texts.get(3).get(i));
                    }
                }
            }
            case "주의할 약 또는 음식"
                ->{
                flagkey = 5;
                if(texts.get(4).get(0).contains("주의할 약 또는 음식")){
                    for(int i = 1; i < texts.get(4).size(); i++){
                        System.out.println(texts.get(4).get(i));
                    }
                }
            }
            case "이상반응"
                ->{
                flagkey = 6;
                if(texts.get(5).get(0).contains("이상반응")){
                    for(int i = 1; i < texts.get(5).size(); i++){
                        System.out.println(texts.get(5).get(i));
                    }
                }
            }
        }*/
//        if(texts.get(0).get(0).contains("효능")){
//            for(int i = 1; i < texts.get(0).size(); i++){
//                System.out.println(texts.get(0).get(i));
//            }
//            //System.out.println(texts);
//        }
//        if(texts.get(1).get(0).contains("사용")){
//            for(int i = 1; i < texts.get(1).size(); i++){
//                System.out.println(texts.get(1).get(i));
//            }
//        }
//        if(texts.get(0).get(0).contains("내용")){
//            for(int i = 1; i < texts.get(0).size(); i++){
//                System.out.println(texts.get(0).get(i));
//            }
//        }
//        if(texts.get(0).get(0).contains("주의사항")){
//            for(int i = 1; i < texts.get(0).size(); i++){
//                System.out.println(texts.get(0).get(i));
//            }
//        }
//        if(texts.get(0).get(0).contains("주의할 약 또는 음식")){
//            for(int i = 1; i < texts.get(0).size(); i++){
//                System.out.println(texts.get(0).get(i));
//            }
//        }
//        if(texts.get(0).get(0).contains("이상반응")){
//            for(int i = 1; i < texts.get(0).size(); i++){
//                System.out.println(texts.get(0).get(i));
//            }
//        }

//        return new ArrayList<>(texts.get(j));


    }

}
