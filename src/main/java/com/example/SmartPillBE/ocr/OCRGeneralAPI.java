package com.example.SmartPillBE.ocr;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

public class OCRGeneralAPI {
    String apiURL = "https://7jsiorbx1j.apigw.ntruss.com/custom/v1/30311/04ab83d4215870a0d025063f80730961413c0f2e4844704d1e88853624aa4397/general";
    String secretKey = "dEd5ZkZScmd2a01XZVB1d3FTR0dKa250cGhUT3dQRE8=";

    public List<String> getOCRResult(String imagePath) {
        List<String> result = new ArrayList<>();
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
//            image should be public, otherwise, should use data
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

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
                result.add(inputLine);
            }
            br.close();

            System.out.println(response);
            return result;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;


    }
}
