//package com.YH.yeohaenghama.domain.test.controller;
//
//import com.YH.yeohaenghama.domain.openApi.dto.OpenApiAreaDTO;
//import com.YH.yeohaenghama.domain.test.dto.TestOpenAPiDTO;
//import io.swagger.v3.oas.annotations.Operation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLEncoder;
//
//@Slf4j
//@RestController
//@RequestMapping("/api/tran")
//public class TranTest {
//
//    @Operation(summary = "대중교통")
//    @PostMapping("/testTran")
//    public String searchArea(@RequestBody TestOpenAPiDTO req) throws IOException {
//        String apiKey = "/l8EmJ0xgdxUdyZT74sfz8sg9y9K9f3Yy8r3SbZYtFc";
//
//        String urlInfo = "https://api.odsay.com/v1/api/searchPubTransPathT?" +
//                "SX=" + req.getSx() +
//                "&SY=" + req.getSy() +
//                "&EX=" + req.getEx() +
//                "&EY=" + req.getEy() +
//                "&SearchPathType=" + req.getSearchPathType() +
//                "&apiKey=" + URLEncoder.encode(apiKey, "UTF-8");
//
//        // http 연결
//        URL url = new URL(urlInfo);
//        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//        conn.setRequestMethod("GET");
//        conn.setRequestProperty("Content-type", "application/json");
//
//        BufferedReader bufferedReader =
//                new BufferedReader(new InputStreamReader(conn.getInputStream()));
//
//        StringBuilder sb = new StringBuilder();
//        String line;
//        while ((line = bufferedReader.readLine()) != null) {
//            sb.append(line);
//        }
//        bufferedReader.close();
//        conn.disconnect();
//        // 결과 출력
//        System.out.println(sb.toString());
//
//        return sb.toString();
//
//    }
//
//        public static void main(String[] args) throws IOException {
//
//        // ODsay Api Key 정보
//        String apiKey = "{YOUR_API_KEY}";
//
//        String urlInfo = "https://api.odsay.com/v1/api/searchPubTransPathT?SX=126.9027279&SY=37.5349277&EX=126.9145430&EY=37.5499421&apiKey=" + URLEncoder.encode(apiKey, "UTF-8");
//
//        // http 연결
//        URL url = new URL(urlInfo);
//        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//        conn.setRequestMethod("GET");
//        conn.setRequestProperty("Content-type", "application/json");
//
//        BufferedReader bufferedReader =
//                new BufferedReader(new InputStreamReader(conn.getInputStream()));
//
//        StringBuilder sb = new StringBuilder();
//        String line;
//        while ((line = bufferedReader.readLine()) != null) {
//            sb.append(line);
//        }
//        bufferedReader.close();
//        conn.disconnect();
//
//        // 결과 출력
//        System.out.println(sb.toString());
//
//    }
//}
