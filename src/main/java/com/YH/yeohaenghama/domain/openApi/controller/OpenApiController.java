package com.YH.yeohaenghama.domain.openApi.controller;

import com.YH.yeohaenghama.common.apiResult.ApiResult;
import com.YH.yeohaenghama.domain.openApi.dto.*;
import com.YH.yeohaenghama.domain.openApi.service.OpenApiService;
import com.YH.yeohaenghama.domain.review.dto.ReviewDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
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
    @PostMapping("/googleSearchKeyword")
    public List<SearchKeywordDTO.Response> searchKeyword(@RequestBody SearchKeywordDTO.Request req) {
        log.info("dto == " + req);
        return openApiService.searchKeyword(req);
    }




    @Operation(summary = "네이버 키워드 검색")
    @PostMapping("/naverSearchKeyword")
    public ApiResult<SearchAreaDTO.Response> test(@RequestBody SearchAreaDTO.Reqeust req) {
        try {
            return ApiResult.success(openApiService.searchAreaNaver(req));
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

//    @Operation(summary = "네이버 리뷰 검색")
//    @PostMapping("/naverSearchReview")
//    public ApiResult<String> testReview(@RequestBody String keyword) {
//        try {
//            return ApiResult.success(openApiService.searchReview(keyword));
//        }catch (Exception e){
//            return ApiResult.fail(e.getMessage());
//        }
//    }



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
            String apiUrl = "https://api.odsay.com/v1/api/searchPubTransPathT?" +
                    "SX=" + req.getSx() +
                    "&SY=" + req.getSy() +
                    "&EX=" + req.getEx() +
                    "&EY=" + req.getEy() +
                    "&SearchPathType=" + req.getSearchPathType();

            String response = openApiService.sendHttpRequest(apiUrl);
            result.append("<xmp>").append(response).append("</xmp>");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();

    }


    @Value("${carKey}")
    String clientId;
    @Value("${carSecret}")
    String clientSecret;

    @Operation(summary = "자동차")
    @PostMapping("getDirections/car")
    public String getDirectionsCar(@RequestBody OpenApiDirectionsDTO.Car req) {
        String uriPath = "https://naveropenapi.apigw.ntruss.com/map-direction/v1/driving";
        String start = req.getStartX() + "," + req.getStartY();
        String goal = req.getGoalX() + "," + req.getGoalY();
        String option = "trafast";


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

        return response;
    }


}
