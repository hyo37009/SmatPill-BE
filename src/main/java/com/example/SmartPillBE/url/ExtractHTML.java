package com.example.SmartPillBE.url;

import com.example.SmartPillBE.service.PillDetailUrlGenerator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

public class ExtractHTML {

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

    public List<String> findInfo(List<String> htmlList){

        List<String> onlyText = new ArrayList<>();
        boolean flag = false;

//        String pillNumber = "202200658";
//        PillDetailUrlGenerator pilldetail = new PillDetailUrlGenerator();
//        String url = pilldetail.generatePillUrl(pillNumber);

//        ExtractHTML extractHTML = new ExtractHTML();
//        List<String> html = extractHTML.getHTML(url);


        for(String result : htmlList){
            String convertingText = Jsoup.clean(result, Whitelist.none());
            if(convertingText.equals("e약은요")) {
                System.out.println("여기!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                System.out.println("여기!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                System.out.println("여기!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                System.out.println("여기!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                System.out.println("여기!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                System.out.println("여기!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                System.out.println("여기!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                flag = true;
            }

            if(flag && !convertingText.isEmpty()){
                if(convertingText.equals("효능효과")) {
                    return onlyText;
                }
                onlyText.add(convertingText);

            }




        }

        return onlyText;



    }

}
