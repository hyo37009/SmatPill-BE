package com.example.SmartPillBE;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.UUID;

public class JSONtest {
    @Test
    void jsonItemTest() throws IOException {
        String apiURL = "https://7jsiorbx1j.apigw.ntruss.com/custom/v1/30311/04ab83d4215870a0d025063f80730961413c0f2e4844704d1e88853624aa4397/general";
        String secretKey = "dEd5ZkZScmd2a01XZVB1d3FTR0dKa250cGhUT3dQRE8=";
        String imageFile = "C:\\Users\\user\\Documents\\github\\SmatPill-BE\\src\\main\\java\\ocr\\KakaoTalk_20240420_202613526_03.jpg";


        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setUseCaches(false);
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setReadTimeout(30000);
        con.setRequestMethod("POST");
        String boundary = "----" + UUID.randomUUID().toString().replaceAll("-", "");
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        con.setRequestProperty("X-OCR-SECRET", secretKey);


        JSONObject json = new JSONObject();
        json.put("version", "V2");
        json.put("requestId", UUID.randomUUID().toString());
        json.put("timestamp", System.currentTimeMillis());
        JSONObject image = new JSONObject();
        image.put("format", "jpg");
        image.put("name", "demo");
        JSONArray images = new JSONArray();
        images.put(image);
        json.put("images", images);
        String postParams = json.toString();

        System.out.println(json);
    }
}
