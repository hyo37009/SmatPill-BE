package com.example.SmartPillBE.service;

import com.example.SmartPillBE.domain.Pill;
import com.example.SmartPillBE.ocr.InferTextExtractor;
import com.example.SmartPillBE.ocr.OCRGeneralAPI;
import com.example.SmartPillBE.repository.PillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FindService {

    List<String> inferTexts;
    @Autowired
    private final PillRepository pillRepository;



    public void searchPill(){
        String imagePath = "C:\\CapstoneProject\\SmatPill-BE\\src\\main\\java\\com\\example\\SmartPillBE\\ocr\\디비약봉지.jpg";

        InferTextExtractor inferTextExtractor = new InferTextExtractor();
        List<String> inferTexts = inferTextExtractor.extractInferText(imagePath);

        System.out.println("inferTexts = " + inferTexts);

        OCRGeneralAPI ocrGeneralAPI = new OCRGeneralAPI();
        List<String> ocrResult = ocrGeneralAPI.getOCRResult(imagePath);
        System.out.println("ocrResult = " + ocrResult);
//
//        List<Pill> result = new ArrayList<>();
//
//        for(String inferText : inferTexts){
//            List<Pill> pills = pillRepository.findByName(inferText);
//
//            if(pills.isEmpty()){
//                result.addAll(pills);
//            }
//
//        }
//        System.out.println("검색된 약:" + result);

    }
}