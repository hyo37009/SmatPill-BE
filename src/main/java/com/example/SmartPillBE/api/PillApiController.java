package com.example.SmartPillBE.api;

import com.example.SmartPillBE.domain.Pill;
import com.example.SmartPillBE.photo.dto.ImageDto;
import com.example.SmartPillBE.photo.service.S3ImageService;
import com.example.SmartPillBE.photo.service.S3Service;
import com.example.SmartPillBE.service.PillService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PillApiController {

    private final PillService pillService;
    private final S3ImageService imageService;
    private final S3Service s3Service;

    @GetMapping("/api/pill/{pillNumber}")
    public PillResponseForApp searchByNumber(@PathVariable("pillNumber") String pillNumber) throws Exception {
        Pill pill = pillService.findByNumber(pillNumber);
        if (pill == null) {
            return null;
        }
        return new PillResponseForApp(pill);
    }

    @GetMapping("/api/pill/name/{pillName}")
    public List<PillResponseForApp> searchByName(@PathVariable("pillName") String pillName) {
        List<Pill> pills = pillService.findByName(pillName);
        return pills.stream()
                .map(PillResponseForApp::new)
                .collect(Collectors.toList());
    }

//    @PostMapping("/api/pill")
//    public List<Pill> searchByImage(@RequestPart(value = "image") MultipartFile image) throws IOException {
//        URL url = new URL("http://27.119.73.203:5000/api/ai/detection");
//        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//        con.setUseCaches(false);
//        con.setDoInput(true);
//        con.setDoOutput(true);
//        con.setRequestMethod("POST");
//        con.setRequestProperty("Content-Type", "multipart/form-data; charset=utf-8");
//
//        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
//        wr.write(Base64.getEncoder().encodeToString(image.getBytes()).getBytes());
//        wr.flush();
//        wr.close();
//
//        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
//        String inputLine;
//        StringBuilder response = new StringBuilder();
//        while ((inputLine = br.readLine()) != null) {
//            response.append(inputLine);
//        }
//        br.close();
//
//        String korResponse = uniToKor(response.toString());
//
//        JsonNode parent = new ObjectMapper().readTree(korResponse);
//        for (int i = 0; i < parent.size(); i++) {
//            JsonNode PillParent = parent.findParent(String.valueOf(i));
//            String pillColor = PillParent.findValuesAsText("pill_color").get(0);
//            String pillShape = PillParent.findValuesAsText("pill_shape").get(0);
//            String pillText = PillParent.findValuesAsText("pill_text").get(0);
//
//            List<Pill> searched = null;
//            if (!pillText.equals("None")) {
//                searched = pillService.findByPrint(pillText);
//            }
////            if ((pillText == null || pillText.equals("null")) &&!byAll.isEmpty()) {
////                return byAll;
////            } else {
//            if( searched == null || searched.isEmpty()){
//                searched = pillService.findByColorAndShape(pillColor, pillShape);
//            }
//            return searched;
//        }
//        return new ArrayList<>();
//    }

    @PostMapping("/api/pill")
    public List<Pill> searchByImage(@RequestPart(value = "image") MultipartFile image) throws Exception {
//        String imageUrl = s3Service.uploadFile(image);
//        imageService.saveDetectionImg(new ImageDto(null, imageUrl, image));
//
//        URL url = new URL("http://27.119.73.203:5000/api/ai/detection");
//        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//        con.setUseCaches(false);
//        con.setDoInput(true);
//        con.setDoOutput(true);
//        con.setRequestMethod("POST");
//        con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
//
//        OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
//        wr.write(imageUrl);
//        wr.flush();
//        wr.close();
//
//        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
//        String inputLine;
//        StringBuilder response = new StringBuilder();
//        while ((inputLine = br.readLine()) != null) {
//            response.append(inputLine);
//        }
//        br.close();
//
//        String korResponse = uniToKor(response.toString());
//
//        JsonNode parent = new ObjectMapper().readTree(korResponse);
//        for (int i = 0; i < parent.size(); i++) {
//            JsonNode PillParent = parent.findParent(String.valueOf(i));
//            String pillColor = PillParent.findValuesAsText("pill_color").get(0);
//            String pillShape = PillParent.findValuesAsText("pill_shape").get(0);
//            String pillText = PillParent.findValuesAsText("pill_text").get(0);
//
//            List<Pill> searched = null;
//            if (!pillText.equals("None")) {
//                searched = pillService.findByPrint(pillText);
//            }
////            if ((pillText == null || pillText.equals("null")) &&!byAll.isEmpty()) {
////                return byAll;
////            } else {
//            if( searched == null || searched.isEmpty()){
//                searched = pillService.findByColorAndShape(pillColor, pillShape);
//            }
//            return searched;
//        }
//        return new ArrayList<>();
        return pillService.findByPrint("EASY");
    }
    private List<Pill> findByImage(String korResponse) throws JsonProcessingException {
        System.out.println("korResponse = " + korResponse);


        return null;
    }

    @Data
    private static class PillResponseForApp {
        private String pillNumber;
        private String pillName;
        private String dosageForm;
        private String effect;
        private String category;
        private String imagePath;

        private PillResponseForApp(Pill pill) {
            this.pillName = pill.getPillName();
            this.pillNumber = pill.getPillNumber();
            this.dosageForm = pill.getDosageForm();
            this.effect = pill.getEffect();
            this.category = pill.getCategory();
            this.imagePath = pill.getImagePath();
        }

    }

    public String uniToKor(String uni) {
        StringBuffer result = new StringBuffer();

        for (int i = 0; i < uni.length(); i++) {
            if (uni.charAt(i) == '\\' && uni.charAt(i + 1) == 'u') {
                Character c = (char) Integer.parseInt(uni.substring(i + 2, i + 6), 16);
                result.append(c);
                i += 5;
            } else {
                result.append(uni.charAt(i));
            }
        }
        return result.toString();
    }


}
