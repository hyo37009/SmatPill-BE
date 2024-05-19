package com.example.SmartPillBE.matcher;

import com.example.SmartPillBE.ocr.OCRGeneralAPI;
import com.example.SmartPillBE.service.PillService;

import java.util.List;

public class OCRToDatabaseMatcher {

    public static void main(String[] args) {
        OCRGeneralAPI ocrGeneralAPI = new OCRGeneralAPI();
        List<String> ocrResults = ocrGeneralAPI.getOCRResult("path_to_your_image.jpg");

        PillService pillService = new PillService();
        List<String> pillNames = pillService.getPillNames();

        for (String ocrText : ocrResults) {
            for (String pillName : pillNames) {
                if (matches(pillName, ocrText)) {
                    System.out.println("Matched Pill: " + pillName);
                }
            }
        }
    }

    private static boolean matches(String pillName, String ocrText) {
        int matchLength = 3;
        int pillNameLength = pillName.length();

        if (pillNameLength < matchLength) {
            return false;
        }

        for (int i = 0; i <= pillNameLength - matchLength; i++) {
            String subStr = pillName.substring(i, i + matchLength);
            if (ocrText.contains(subStr)) {
                return true;
            }
        }

        return false;
    }
}
