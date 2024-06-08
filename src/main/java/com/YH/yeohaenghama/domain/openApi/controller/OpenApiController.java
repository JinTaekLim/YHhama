package com.YH.yeohaenghama.domain.openApi.controller;

import com.YH.yeohaenghama.common.apiResult.ApiResult;
import com.YH.yeohaenghama.domain.openApi.dto.*;
import com.YH.yeohaenghama.domain.openApi.service.OpenApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/openApi")
public class OpenApiController {


    private final OpenApiService openApiService;


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 접근"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })




    @Operation(summary = "구글 키워드 검색")
    @PostMapping("/searchKeyword")
    public List<SearchKeywordDTO.Response> searchKeyword(@RequestBody SearchKeywordDTO.Request req) {
        log.info("dto == " + req);
        return openApiService.searchKeyword(req);
    }



    @Operation(summary = "tt")
    @PostMapping("/tt")
    public void tt(@RequestBody SearchAreaDTO.Reqeust req) throws Exception {

        String GEOCODE_URL = "https://dapi.kakao.com/v2/local/search/address.json";
        String REST_API_KEY = "a9d1711e66ed62d5be76957294ab0a9f"; // Kakao REST API 키

        String query = URLEncoder.encode("인천광역시 중구 북성동2가 14", "UTF-8");
        String urlString = GEOCODE_URL + "?query=" + query;
        URL url = new URL(urlString);

        // HTTP 연결 설정
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", "KakaoAK " + REST_API_KEY);

        // 응답 읽기
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // JSON 파싱
        String json = response.toString();
        int xIndex = json.indexOf("\"x\":\"") + 5;
        int yIndex = json.indexOf("\"y\":\"") + 5;
        String x = json.substring(xIndex, json.indexOf("\"", xIndex));
        String y = json.substring(yIndex, json.indexOf("\"", yIndex));

        // 결과 출력
        System.out.println("x: " + x + ", y: " + y);


    }

    @Operation(summary = "test")
    @PostMapping("/test")
    public ApiResult<SearchAreaDTO.Response> test(@RequestBody SearchAreaDTO.Reqeust req) {
        try {
            return ApiResult.success(openApiService.searchAreaNaver(req));
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }



    @Operation(summary = "관광지/음식점 키워드 검색")
    @PostMapping("/searchArea")
    public ApiResult<SearchAreaDTO.Response> searchArea(@RequestBody SearchAreaDTO.Reqeust req) {
        try {
            return ApiResult.success(openApiService.searchArea(req));
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }



    @Operation(summary = "관광지/음식점 상세 조회 ")
    @PostMapping("/searchDetail")
    public ApiResult<SearchDetailDTO.Response> searchDetail(@RequestBody OpenApiDetailDTO req) {
        try {
            return ApiResult.success(openApiService.searchDetail(req));
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "관광지/음식점 사진 조회")
    @PostMapping("/searchImage")
    public String searchImage(@RequestBody OpenApiImageDTO req) {
        StringBuffer result = new StringBuffer();
        try {
            String apiUrl = "https://apis.data.go.kr/B551011/KorService1/detailImage1?" +
                    "serviceKey=" + openApiService.getServiceKey() +
                    "&MobileOS=" + req.getMobileOS() +
                    "&MobileApp=AppTest" +
                    "&_type=json" +
                    "&contentId=" + req.getContentId() +
                    "&imageYN=Y" +
                    "&subImageYN=Y" +
                    "&numOfRows=" + req.getNumOfRows() +
                    "&pageNo=" + req.getPageNo();

            String response = openApiService.sendHttpRequest(apiUrl);
            result.append("<xmp>").append(response).append("</xmp>");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }




    @Operation(summary = "위치 조회")
    @PostMapping("/searchLocation")
    public String searchLocation(@RequestBody OpenApiLocationDTO req) {
        StringBuffer result = new StringBuffer();
        try {
            String apiUrl = "https://apis.data.go.kr/B551011/KorService1/" +
                    "locationBasedList1?" +
                    "serviceKey=" + openApiService.getServiceKey() +
                    "&numOfRows=" + req.getNumOfRows() +
                    "&pageNo=" + req.getPage() +
                    "&MobileOS=" + req.getMobileOS() +
                    "&MobileApp=AppTest" +
                    "&_type=json" +
                    "&listYN=Y" +
                    "&arrange=A" +
                    "&mapX=" + req.getMapX() +
                    "&mapY=" + req.getMapY() +
                    "&radius=" + req.getRadius() +
                    "&contentTypeId=" + req.getContentTypeId();


            String response = openApiService.sendHttpRequest(apiUrl);
            result.append("<xmp>").append(response).append("</xmp>");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    @Operation(summary = "대중교통")
    @PostMapping("getDirections/transport")
    public String getDirectionsTransporrt(@RequestBody OpenApiDirectionsDTO req) throws IOException {
        StringBuffer result = new StringBuffer();
        try {
            String apiKey = "/l8EmJ0xgdxUdyZT74sfz8sg9y9K9f3Yy8r3SbZYtFc";

            String apiUrl = "https://api.odsay.com/v1/api/searchPubTransPathT?" +
                    "SX=" + req.getSx() +
                    "&SY=" + req.getSy() +
                    "&EX=" + req.getEx() +
                    "&EY=" + req.getEy() +
                    "&SearchPathType=" + req.getSearchPathType() +
                    "&apiKey=" + URLEncoder.encode(apiKey, "UTF-8");

            String response = openApiService.sendHttpRequest(apiUrl);
            result.append("<xmp>").append(response).append("</xmp>");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();

    }


    @Operation(summary = "자동차")
    @PostMapping("getDirections/car")
    public String getDirectionsCar(@RequestBody OpenApiDirectionsDTO.Car req) {
        String uriPath = "https://naveropenapi.apigw.ntruss.com/map-direction/v1/driving";
        String start = req.getStartX() + "," + req.getStartY();
        String goal = req.getGoalX() + "," + req.getGoalY();
        String option = "trafast";
        String clientId = "vc01qvu4yb";
        String clientSecret = "pVNU6Mh1UIC1Sq0EEm805k5EHLV1GtPKne2or3A1";

        String apiUrl = UriComponentsBuilder.fromHttpUrl(uriPath)
                .queryParam("start", start)
                .queryParam("goal", goal)
                .queryParam("option", option)
                .toUriString();

        WebClient webClient = WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader("X-NCP-APIGW-API-KEY-ID", clientId)
                .defaultHeader("X-NCP-APIGW-API-KEY", clientSecret)
                .build();

        String response = webClient.get()
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println(" response : " + response);
        return response;
    }


//    @Operation(summary = "관광지/음식점 키워드 검색 ( 요약 반환 ) ")
//    @PostMapping("/searchArea")
//    public List<OpenApiAreaDTO.Response.Body.Items.Item> test(@RequestBody OpenApiAreaDTO req) throws Exception {
//        return openApiService.searchAreaAndGetResponse(req);
//    }


}
