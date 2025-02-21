package com.YH.yeohaenghama.domain.openApi.service;

import com.YH.yeohaenghama.domain.addPlace.entity.AddPlace;
import com.YH.yeohaenghama.domain.addPlace.service.AddPlaceService;
import com.YH.yeohaenghama.domain.openApi.dto.*;
import com.YH.yeohaenghama.domain.review.dto.ReviewDTO;
import com.YH.yeohaenghama.domain.review.entity.Review;
import com.YH.yeohaenghama.domain.review.repository.ReviewRepository;
import com.YH.yeohaenghama.domain.review.service.ReviewService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OpenApiService {
    private final AddPlaceService addPlaceService;
    private final ReviewRepository reviewRepository;

    private final String GOOGLE_MAPS_API_URL = "https://maps.googleapis.com/maps/api/place/textsearch/json";
    private final String KAKAO_GEOCODE_URL = "https://dapi.kakao.com/v2/local/search/address.json";

    @Value("${AreaKey}")
    private String serviceKey;
    @Value("${googleMapKey}")
    private String serviceKeyG;
    @Value("${naver.clientId}")
    private String naverClient;
    @Value("${naver.secret}")
    private String naverSecret;
    @Value("${naver.restKey}")
    String KAKAO_REST_API_KEY;
    @Value("${transportKey}")
    String transportKey;

    public List<SearchKeywordDTO.Response> searchKeyword(SearchKeywordDTO.Request req){
        String url = String.format("%s?query=%s&language=ko&key=%s", GOOGLE_MAPS_API_URL, req.getKeyword(), serviceKeyG);

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        log.info(response);

        return null;
    }



    public SearchAreaDTO.Response searchArea(SearchAreaDTO.Reqeust req) throws Exception {
        String encodedKeyword = URLEncoder.encode(req.getKeyword(), StandardCharsets.UTF_8.name());
        String apiUrl = String.format(
                "https://apis.data.go.kr/B551011/KorService1/searchKeyword1?serviceKey=%s&numOfRows=%s&pageNo=%s&MobileOS=%s&MobileApp=AppTest&_type=json&listYN=Y&arrange=A&keyword=%s&contentTypeId=%s",
                serviceKey, req.getNumOfRows(), req.getPage(), req.getMobileOS(), encodedKeyword, req.getContentTypeId()
        );

        String url = sendHttpRequest(apiUrl);

        SearchAreaDTO.Response response = SearchAreaDTO.Response.parsing(url);

        if(response.getPlace().isEmpty()) {
            response = searchAreaNaver(req);
        }
        return response;
    }

    public SearchAreaDTO.Response searchArea(String keyword) {
        try {
            String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8.name());
            String apiUrl = String.format(
                "https://apis.data.go.kr/B551011/KorService1/searchKeyword1?serviceKey=%s&numOfRows=%s&pageNo=%s&MobileOS=%s&MobileApp=AppTest&_type=json&listYN=Y&arrange=A&keyword=%s&contentTypeId=%s",
                serviceKey, 10, 0, "ETC", encodedKeyword, 12
            );

            String url = sendHttpRequest(apiUrl);

            return SearchAreaDTO.Response.parsing(url);
        } catch (Exception e){
            return null;
        }
    }


    public SearchDetailDTO.Response searchDetail(OpenApiDetailDTO req) throws Exception {
        if(req.getContentTypeId().equals("80")) { return addPlaceDetail(req.getContentId()); }

        String apiUrl = "https://apis.data.go.kr/B551011/KorService1/detailCommon1?" + "serviceKey=" + serviceKey + "&MobileOS=" + req.getMobileOS() + "&MobileApp=AppTest" + "&_type=json" + "&contentId=" + req.getContentId() + "&contentTypeId=" + req.getContentTypeId() + "&defaultYN=Y" + "&firstImageYN=Y" + "&areacodeYN=Y" + "&catcodeYN=Y" + "&addrinfoYN=Y" + "&mapinfoYN=Y" + "&overviewYN=Y" + "&numOfRows=" + req.getNumOfRows() + "&pageNo=" + req.getPageNo();

        String url = sendHttpRequest(apiUrl);

        SearchDetailDTO.Response response = SearchDetailDTO.Response.parse(url);
        return response;
    }

    public SearchDetailDTO.Response addPlaceDetail(String contentId){
        AddPlace addPlace = addPlaceService.getAddPlaceInfo(contentId);

        List<Review> reviewList = reviewRepository.findByContentTypeIdAndContentId(80L,Long.valueOf(contentId));

        String photoUrl = "";
        if(!reviewList.isEmpty()){
            if (!reviewList.get(0).getReviewPhotoURLS().isEmpty()){
                photoUrl = (reviewList.get(0).getReviewPhotoURLS().get(0).getPhotoUrl());
            }
        }
        SearchDetailDTO.Response response = SearchDetailDTO.Response.parse(addPlace,photoUrl);
        return response;
    }



    public SearchAreaDTO.Response searchAreaNaver(SearchAreaDTO.Reqeust req) throws Exception {
        SearchAreaDTO.Response response = null;
        String encodedKeyword = URLEncoder.encode(req.getKeyword(), "UTF-8");
        String apiUrl = "https://openapi.naver.com/v1/search/local?query=" + encodedKeyword;

        URL url = new URL(apiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("X-Naver-Client-Id", naverClient);
        con.setRequestProperty("X-Naver-Client-Secret", naverSecret);

        int responseCode = con.getResponseCode();

        if (responseCode == 200) {
            StringBuilder result = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    result.append(inputLine.trim());
                }
            }

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(result.toString());


            response = new SearchAreaDTO.Response();
            response.setNumOfRows(rootNode.path("total").asInt());
            response.setPageNo(rootNode.path("start").asInt());
            response.setTotalCount(rootNode.path("display").asInt());

            JsonNode itemsNode = rootNode.path("items").get(0);
            List<SearchAreaDTO.info> infoList = new ArrayList<>();
            SearchAreaDTO.info info = new SearchAreaDTO.info();
            if (itemsNode != null) {
                String title = Jsoup.parse(itemsNode.path("title").asText()).text();
                String add1 = itemsNode.path("address").asText();
                String add2 = itemsNode.path("roadAddress").asText();
                String tel = itemsNode.path("telephone").asText();

//                String mapx = itemsNode.path("mapx").asText();
//                String mapy = itemsNode.path("mapy").asText();


                String coordinate = changeXY(add1);

                int xIndex = coordinate.indexOf("\"x\":\"") + 5;
                int yIndex = coordinate.indexOf("\"y\":\"") + 5;
                String x = coordinate.substring(xIndex, coordinate.indexOf("\"", xIndex));
                String y = coordinate.substring(yIndex, coordinate.indexOf("\"", yIndex));

                AddPlace addPlace = addPlaceService.getAddPlace(title, add1 ,add2 , tel ,x , y);


                info.setContentid(String.valueOf(addPlace.getId()));
                info.setContenttypeid("80");
                info.setTitle(addPlace.getTitle());
                info.setTel(addPlace.getTel());
                info.setAddr1(addPlace.getAdd1());
                info.setAddr2(addPlace.getAdd2());
                info.setMapx(addPlace.getMapX());
                info.setMapy(addPlace.getMapY());
                infoList.add(info);
            }
            response.setPlace(infoList);
        }
        return response;
    }

    public String searchReview(String title) throws Exception {
        String encodedKeyword = URLEncoder.encode(title, "UTF-8");
        String apiUrl = "https://openapi.naver.com/v1/search/blog?query=" + encodedKeyword;

        URL url = new URL(apiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("X-Naver-Client-Id", naverClient);
        con.setRequestProperty("X-Naver-Client-Secret", naverSecret);

        int responseCode = con.getResponseCode();

        StringBuilder result = new StringBuilder();

        if (responseCode == 200) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    result.append(inputLine.trim());
                }
            }
        }

        return result.toString();
    }

    public String changeXY(String add) throws Exception {
        String query = URLEncoder.encode(add, "UTF-8");
        String urlString = KAKAO_GEOCODE_URL + "?query=" + query;
        URL url = new URL(urlString);

        // HTTP 연결 설정
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", "KakaoAK " + KAKAO_REST_API_KEY);

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
//        int xIndex = json.indexOf("\"x\":\"") + 5;
//        int yIndex = json.indexOf("\"y\":\"") + 5;
//        String x = json.substring(xIndex, json.indexOf("\"", xIndex));
//        String y = json.substring(yIndex, json.indexOf("\"", yIndex));

        // 결과 출력
//        System.out.println("x: " + x + ", y: " + y);
        return json;
    }


    public String getServiceKey(){
        return serviceKey;
    }

    public OpenApiGetXY.Response getXY(String contentId,String contentType) {
        StringBuffer result = new StringBuffer();
        try {
            String apiUrl = "https://apis.data.go.kr/B551011/KorService1/detailCommon1?" +
                    "serviceKey=" + serviceKey +
                    "&MobileOS=ETC" +
                    "&MobileApp=AppTest" +
                    "&_type=json" +
                    "&contentId=" + contentId +
                    "&contentTypeId=" + contentType +
                    "&defaultYN=Y" +
                    "&firstImageYN=Y" +
                    "&areacodeYN=Y" +
                    "&catcodeYN=Y" +
                    "&addrinfoYN=Y" +
                    "&mapinfoYN=Y" +
                    "&overviewYN=Y" +
                    "&numOfRows=1" +
                    "&pageNo=1";

            String response = sendHttpRequest(apiUrl);

            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode rootNode = objectMapper.readTree(response);


            OpenApiGetXY.Response openApiGetXY = new OpenApiGetXY.Response();

            for (JsonNode itemNode : rootNode.path("response").path("body").path("items").path("item")) {

                String mapx = itemNode.path("mapx").asText();
                String mapy = itemNode.path("mapy").asText();

                log.info("x = "  + mapx + " /   y = " + mapy);
                openApiGetXY.setX(mapx);
                openApiGetXY.setY(mapy);
            }

            return openApiGetXY;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getDirectionsTransport(OpenApiDirectionsDTO req) {
        StringBuffer result = new StringBuffer();
        try {

            String apiUrl = "https://api.odsay.com/v1/api/searchPubTransPathT?" +
                    "SX=" + req.getSx() +
                    "&SY=" + req.getSy() +
                    "&EX=" + req.getEx() +
                    "&EY=" + req.getEy() +
                    "&SearchPathType=" + req.getSearchPathType() +
                    "&apiKey=" + URLEncoder.encode(transportKey, "UTF-8");

            String response = sendHttpRequest(apiUrl);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode resultNode = rootNode.get("result");
            if (resultNode != null) {
                JsonNode pathNode = resultNode.get("path");
                if (pathNode != null && pathNode.isArray() && pathNode.size() > 0) {
                    int totalTime = pathNode.get(0).get("info").get("totalTime").asInt();
                    return String.valueOf(totalTime);
                }
            }
            return "-1";
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }


    public String sendHttpRequest(String apiUrl) throws Exception {
        apiUrl += "&apiKey=" + URLEncoder.encode(transportKey, "UTF-8");

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
