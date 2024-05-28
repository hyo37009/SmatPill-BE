package com.example.SmartPillBE.service.getPillInfo;

import com.example.SmartPillBE.url.ExtractHTML;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExtractHtmlService {
    private final ExtractHTML extractHTML = new ExtractHTML();

    private String generateUrl(String pillNumber){
        return extractHTML.generatePillUrl(pillNumber);
    }

    public List<List<String>> getResult(String pillNumber) {
        List<String> html = extractHTML.getHTML(generateUrl(pillNumber));
        return extractHTML.findInfo(html);
//
//        List<String> key1 = new ArrayList<>(Arrays.asList("이 약의 효능은 무엇입니까?", "이 약은 어떻게 사용합니까?", "이 약을 사용하기 전에 반드시 알아야 할 내용은 무엇입니까?", "이 약의 사용상 주의사항은 무엇입니까?", "이 약을 사용하는 동안 주의해야 할 약 또는 음식은 무엇입니까?", "이 약은 어떤 이상반응이 나타날 수 있습니까?"));
//
//        List<String> key2 = new ArrayList<>(Arrays.asList("효능효과", "용법용량", "다음 환자에는 투여하지 말 것.", "다음 환자에는 신중히 투여할 것.", "이상반응", "부작용", "일반적 주의", "상호작용"));
//
//
//
//        List<List<String>> result = null;
//        for (String key : key1) {
//            List<List<String>> lists = extractHTML.returnText(key, info);
//            if(!lists.isEmpty()){
//                result.add(lists.get(0));
//            }
//        }
//
//        for (String key : key2) {
//            List<List<String>> lists = extractHTML.returnText(key, info);
//            if(!lists.isEmpty()){
//                result = lists;
//            }
//        }


//        return result;


    }

}
