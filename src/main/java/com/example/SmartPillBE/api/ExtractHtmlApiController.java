package com.example.SmartPillBE.api;

import com.example.SmartPillBE.service.getPillInfo.ExtractHtmlService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExtractHtmlApiController {
    private final ExtractHtmlService extractHtmlService;

    @GetMapping("/api/pill/{pillNumber}/detail")
    public List<PillDetailDto> getPillDetail(@PathVariable("pillNumber") String pillNumber){
        List<List<String>> results = extractHtmlService.getResult(pillNumber);
        List<PillDetailDto> returnList = new ArrayList<>();
        for (List<String> result : results) {
            returnList.add(new PillDetailDto(result));
        }
        return returnList;
    }

    @Data
    @AllArgsConstructor
    private class PillDetailDto {
        String category;
        String contents;

        public PillDetailDto(List<String> stringList) {
            category = stringList.get(0);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 1; i < stringList.size(); i++){
                stringBuilder.append(stringList.get(i));
                stringBuilder.append("\n");
            }
            this.contents = stringBuilder.toString();
        }
    }
}
