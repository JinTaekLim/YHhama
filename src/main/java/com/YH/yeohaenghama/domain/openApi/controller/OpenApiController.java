package com.YH.yeohaenghama.domain.openApi.controller;

import com.YH.yeohaenghama.domain.openApi.dto.OpenApiAreaDTO;
import com.YH.yeohaenghama.domain.openApi.dto.OpenApiDetailDTO;
import com.YH.yeohaenghama.domain.openApi.dto.OpenApiImageDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
@Slf4j
@RestController
@RequestMapping("/api/openApi")
public class OpenApiController {

    String serviceKey = "%2B1I%2BbTxxqsKlIjXBgNQX38e6gZOJnlCyPLnkFQUQFrpoCl9tEcII2L%2BvUeJuiaAFf3bN1wly8A6VzOw%2FGz9v7w%3D%3D";


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 접근"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })

    @PostMapping("/searchArea")
    public String searchArea(@RequestBody OpenApiAreaDTO req) {
        StringBuffer result = new StringBuffer();
        try {
            String encodedKeyword = URLEncoder.encode(req.getKeyword(), "UTF-8");
            String apiUrl = "https://apis.data.go.kr/B551011/KorService1/" +
                    "searchKeyword1?" +
                    "serviceKey=" + serviceKey +
                    "&numOfRows=" + req.getNumOfRows() +
                    "&pageNo=" + req.getPage() +
                    "&MobileOS=" + req.getMobileOS() +
                    "&MobileApp=AppTest" +
                    "&_type=json" +
                    "&listYN=Y" +
                    "&arrange=A" +
                    "&keyword=" + encodedKeyword +
                    "&contentTypeId=" + req.getContentTypeId();


            String response = sendHttpRequest(apiUrl);
            result.append("<xmp>").append(response).append("</xmp>");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }


    @PostMapping("/searchDetail")
    public String searchDetail(@RequestBody OpenApiDetailDTO req) {
        StringBuffer result = new StringBuffer();
        try {
            String apiUrl = "https://apis.data.go.kr/B551011/KorService1/detailCommon1?" +
                    "serviceKey=" + serviceKey +
                    "&MobileOS=" + req.getMobileOS() +
                    "&MobileApp=AppTest" +
                    "&_type=json" +
                    "&contentId=" + req.getContentId() +
                    "&contentTypeId=" + req.getContentTypeId() +
                    "&defaultYN=Y" +
                    "&firstImageYN=Y" +
                    "&areacodeYN=Y" +
                    "&catcodeYN=Y" +
                    "&addrinfoYN=Y" +
                    "&mapinfoYN=Y" +
                    "&overviewYN=Y" +
                    "&numOfRows=" + req.getNumOfRows() +
                    "&pageNo=" + req.getPageNo();




            String response = sendHttpRequest(apiUrl);
            result.append("<xmp>").append(response).append("</xmp>");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    @PostMapping("/searchImage")
    public String searchImage(@RequestBody OpenApiImageDTO req) {
        StringBuffer result = new StringBuffer();
        try {
            String apiUrl = "https://apis.data.go.kr/B551011/KorService1/detailImage1?" +
                    "serviceKey=" + serviceKey +
                    "&MobileOS=" + req.getMobileOS() +
                    "&MobileApp=AppTest" +
                    "&_type=json" +
                    "&contentId=" + req.getContentId() +
                    "&imageYN=Y" +
                    "&subImageYN=Y" +
                    "&numOfRows=" + req.getNumOfRows() +
                    "&pageNo=" + req.getPageNo();

            String response = sendHttpRequest(apiUrl);
            result.append("<xmp>").append(response).append("</xmp>");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }





    private String sendHttpRequest(String apiUrl) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
        StringBuilder response = new StringBuilder();
        String returnLine;
        while ((returnLine = bufferedReader.readLine()) != null) {
            response.append(returnLine).append("\n");
        }
        urlConnection.disconnect();
        return response.toString();
    }
}
