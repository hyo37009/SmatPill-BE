package com.example.SmartPillBE.service.getPillInfo;

import com.example.SmartPillBE.domain.Pill;
import com.example.SmartPillBE.ocr.OCRGeneralAPI;
import com.example.SmartPillBE.repository.PillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;


@Service
public class FindPill {
    /*private String inferText;
    private double inferConfidence;

    public InferText(String inferText, double inferConfidence) {
        this.inferText = inferText;
        this.inferConfidence = inferConfidence;
    }

    public String getInferText() {
        return inferText;
    }

    public double getInferConfidence() {
        return inferConfidence;
    }


    public static void main(String[] args) {
        // 주어진 JSON 데이터 예시
        JSONObject result = new JSONObject("{\"images\":[{\"fields\":[{\"inferText\":\"Text1\"},{\"inferText\":\"Text2\"}]}]}");

        // 결과를 저장할 빈 문자열 초기화
        StringBuilder text = new StringBuilder();

        // 'images' 배열에서 첫 번째 요소의 'fields' 배열을 반복하여 텍스트 추출
        JSONArray fields = result.getJSONArray("images").getJSONObject(0).getJSONArray("fields");
        for (int i = 0; i < fields.length(); i++) {
            JSONObject field = fields.getJSONObject(i);
            // 각 필드의 'inferText' 값을 문자열에 추가
            text.append(field.getString("inferText"));
        }

        // 최종적으로 추출된 텍스트 출력
        System.out.println(text.toString());
    }*/




    private final PillRepository pillRepository;
    static OCRGeneralAPI ocrGeneralAPI = new OCRGeneralAPI();

    public FindPill(PillRepository pillRepository) {
        this.pillRepository = pillRepository;
    }

    public static void main(String[] args) {
        List<String> ocrResult = ocrGeneralAPI.getOCRResult("C:\\CapstoneProject\\SmatPill-BE\\src\\main\\java\\com\\example\\SmartPillBE\\ocr\\asd.jpg");

        System.out.println(ocrResult);

    }

    /*@Autowired
    public FindPill(PillRepository pillRepository) {
        this.pillRepository = pillRepository;
    }

    public void printPillInfoFromDB(List<String> pillIds) {
        for (String pillId : pillIds) {
            Pill pill = pillRepository.findOne(pillId); // 데이터베이스에서 해당 ID의 약 정보를 가져옴
            if (pill != null) {
                System.out.println("약 번호: " + pill.getId());
                System.out.println("약 이름: " + pill.getPillName());
                System.out.println("약 설명: " + pill.getDescription());
                // 필요한 다른 약 정보를 출력하거나 처리할 수 있음
            } else {
                System.out.println("ID가 " + pillId + "인 약 정보를 데이터베이스에서 찾을 수 없습니다.");
            }
        }
    }*/

}