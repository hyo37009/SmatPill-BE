package com.example.SmartPillBE.ocr;

import java.util.List;

public class FindPill {

    String FindPill(){
        OCRGeneralAPIDemo ocrGeneralAPI = new OCRGeneralAPIDemo();
        List<String> ocrResult = ocrGeneralAPI.getOCRResult("이미지 주소");
        for (String string : ocrResult) {
            if (string in 데이터베이스)
                약 이름 = string;
                약 고유번호 = 데이터베이스.약.약고유번호;
                break;
        }
        return 약고유번호;
    }
}
