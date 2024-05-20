package com.example.SmartPillBE.url;

import com.example.SmartPillBE.service.PillDetailUrlGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExtractHTMLTest {
    @Test
    void extractHTML() {

        String pillNumber = "202200658";


        PillDetailUrlGenerator pilldetail = new PillDetailUrlGenerator();
        String url = pilldetail.generatePillUrl(pillNumber);

        System.out.println("url = " + url);

        ExtractHTML extractHTML = new ExtractHTML();
        List<String> html = extractHTML.getHTML(url);

        List<String> onlyText = extractHTML.findInfo(html);

        for (String s : html) {
            System.out.println("s = " + s);
        }

        for(String t : onlyText){
            System.out.println("t = " + t);
        }

        //List<String> text = extractHTML.findInfo(onlyText);
    }

}