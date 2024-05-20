
package com.example.SmartPillBE.ocr;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InferTextExtractor {

    String apiURL = "https://7jsiorbx1j.apigw.ntruss.com/custom/v1/30311/04ab83d4215870a0d025063f80730961413c0f2e4844704d1e88853624aa4397/general";
    String secretKey = "dEd5ZkZScmd2a01XZVB1d3FTR0dKa250cGhUT3dQRE8=";

    public List<String> extractInferText(String imagePath) {
        List<String> inferTexts = new ArrayList<>();
        try {
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con.setRequestProperty("X-OCR-SECRET", secretKey);

            JSONObject json = new JSONObject();
            json.put("version", "V2");
            json.put("requestId", UUID.randomUUID().toString());
            json.put("timestamp", System.currentTimeMillis());
            JSONObject image = new JSONObject();
            image.put("format", "jpg");

            // 이미지 파일을 바이트 배열로 변환하여 전송
            File file = new File(imagePath);
            FileInputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            image.put("data", buffer);
            image.put("name", "demo");
            JSONArray images = new JSONArray();
            images.put(image);
            json.put("images", images);
            String postParams = json.toString();

            // HTTP 요청 전송
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();

            // HTTP 응답 처리
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();

                // JSON 파싱하여 inferText 값만 추출
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray fields = jsonResponse.getJSONArray("images").getJSONObject(0).getJSONArray("fields");
                for (int i = 0; i < fields.length(); i++) {
                    JSONObject field = fields.getJSONObject(i);
                    inferTexts.add(field.getString("inferText"));
                }
            } else {
                System.out.println("HTTP error: " + responseCode);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return inferTexts;
    }
}


