package com.example.SmartPillBE.service;

import com.example.SmartPillBE.domain.Pill;
import com.example.SmartPillBE.ocr.InferTextExtractor;
import com.example.SmartPillBE.ocr.OCRGeneralAPI;
import com.example.SmartPillBE.repository.PillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FindService {

    List<String> inferTexts;
    @Autowired
    private final PillRepository pillRepository;

    private List<String> keys = Arrays.asList("당의정","필름코팅정","정제","나정","스팬슐","연질캡슐제","추어블정","저작정","경질캡슐제","연질캡슐제","정제","캡슐","서방성캡슐제","펠렛","경질캡슐제","과립제","다층정","구강붕해정","장용성필름코팅정","서방성필름코팅정","장용정","트로키제","서방성장용필름코팅정","서방정","경질캡슐제","장용성과립제","장용성캡슐제","정제","흡입제","미분류","젤라틴코팅성경질캡슐제","장용성캡슐제","펠렛","분산정","현탁정","설하정","경질캡슐제","과립제정제","경질캡슐제","서방성장용성펠렛","구강붕해필름","캡슐","장용성당의정","질정","질연질캡슐제","장용성필름코팅당의정","경질캡슐제","정제","장용성필름코팅캡슐제","질좌제","껌제","서방성다층정","발포정","산제","지지체가있는첩부제","정량흡입제","분말제","박칼정","정량분말분무제","부착정","유핵정","경질캡슐제","공캡슐","현탁상");


    public void searchPill(){
        String imagePath = "C:\\CapstoneProject\\SmatPill-BE\\src\\main\\java\\com\\example\\SmartPillBE\\ocr\\홍길동.jpg";

        InferTextExtractor inferTextExtractor = new InferTextExtractor();
        List<String> inferTexts = inferTextExtractor.extractInferText(imagePath);

        System.out.println("inferTexts = " + inferTexts);

        OCRGeneralAPI ocrGeneralAPI = new OCRGeneralAPI();
        List<String> ocrResult = ocrGeneralAPI.getOCRResult(imagePath);
        System.out.println("ocrResult = " + ocrResult);


        List<Pill> result = new ArrayList<>();
        for(int i=0; i<inferTexts.size(); i++){

            String searchText = inferTexts.get(i);



            if(searchText.contains("(")){
                searchText = searchText.substring(0,searchText.indexOf("("));
            }



            if(searchText.length() >= 3){//3글자 이상을 약이라고 간주

                searchText = searchText.replace("mg", "");


                if(searchText.replaceAll("[0-9]", "").isBlank() || keys.contains(searchText)){
                    continue;
                }

                List<Pill> pills = pillRepository.findByName(searchText); //inferTexts에 있는 검색된 약을 pills에 담음

                //System.out.println("searchText = " + searchText);

                if(!pills.isEmpty()){ //pills가 비어 있지 않다 = pills에 약이 있다.
                    result.addAll(pills);
                    System.out.println("검색텍스트는:" + searchText); //+ "\n검색된 약:" + pills);
                }
            }

        }

        List<Pill> newResult = new ArrayList<>();
        List<String> t = new ArrayList<>();

        for (Pill pill : result) {
            if(!t.contains(pill.getPillName())){
                newResult.add(pill);
                t.add(pill.toString());
            }
        }

        for (Pill pill : newResult) {
            System.out.println("pill = " + pill);
        }
//        System.out.println("검색된 약:" + result);


//        List<Pill> pills = pillRepository.findByName("페니라민정");
//        System.out.println(pills);

        List<Pill> check1 = pillRepository.findByName("올메텍플러스정");
        List<Pill> check2 = pillRepository.findByName("올메텍플러스정20/");
        List<Pill> check3 = pillRepository.findByName("올메텍플러스정20/12.5mg");
        System.out.println(check1);
        System.out.println(check2);
        System.out.println(check3);

        for (String inferText : inferTexts) {
            System.out.println("inferText = " + inferText);
        }


//        List<Pill> result = new ArrayList<>();

//        for(String inferText : inferTexts){

//            List<Pill> pills = pillRepository.findByName(inferText);

//            if(pills.isEmpty()){
//                result.addAll(pills);
//            }

//        }
//        System.out.println("검색된 약:" + result);

    }
}