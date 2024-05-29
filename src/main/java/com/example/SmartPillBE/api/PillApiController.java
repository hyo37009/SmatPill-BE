package com.example.SmartPillBE.api;

import com.example.SmartPillBE.domain.Pill;
import com.example.SmartPillBE.service.PillService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PillApiController {

    private final PillService pillService;

    @GetMapping("/api/pill/{pillNumber}")
    public PillResponseForApp searchByNumber(@PathVariable("pillNumber") String pillNumber) throws Exception {
        Pill pill = pillService.findByNumber(pillNumber);
        if(pill == null){
            return null;
        }
        return new PillResponseForApp(pill);
    }

    @GetMapping("/api/pill/name/{pillName}")
    public List<PillResponseForApp> searchByName(@PathVariable("pillName") String pillName){
        List<Pill> pills = pillService.findByName(pillName);
        return pills.stream()
                .map(PillResponseForApp::new)
                .collect(Collectors.toList());
    }

    @PostMapping("/api/pill/")
    public List<Pill> searchByImage(@RequestPart(value = "image")MultipartFile image) throws IOException {
        URL url = new URL("http://15.165.129.252:5000/api/ai/detection");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setUseCaches(false);
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "multipart/form-data; charset=utf-8");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("image", image);

        String body = jsonObject.toString();

        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.write(body.getBytes("utf-8"));
        wr.flush();
        wr.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }
        br.close();

        String korResponse = uniToKor(response.toString());

        return null;
    }

    @Data
    private static class PillResponseForApp{
        private String pillNumber;
        private String pillName;
        private String dosageForm;
        private String effect;
        private String category;
        private String imagePath;

        private PillResponseForApp(Pill pill){
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
