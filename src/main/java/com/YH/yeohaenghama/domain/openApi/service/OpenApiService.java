package com.YH.yeohaenghama.domain.openApi.service;

import com.YH.yeohaenghama.domain.openApi.dto.OpenApiAreaDTO;
import com.YH.yeohaenghama.domain.openApi.dto.OpenApiDetailDTO;
import com.YH.yeohaenghama.domain.openApi.dto.OpenApiDirectionsDTO;
import com.YH.yeohaenghama.domain.openApi.dto.OpenApiGetXY;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OpenApiService {

    private String serviceKey = "%2B1I%2BbTxxqsKlIjXBgNQX38e6gZOJnlCyPLnkFQUQFrpoCl9tEcII2L%2BvUeJuiaAFf3bN1wly8A6VzOw%2FGz9v7w%3D%3D";




        public List<OpenApiAreaDTO.Response.Body.Items.Item> searchAreaAndGetResponse(OpenApiAreaDTO dto) throws Exception {
            try {
                String encodedKeyword = URLEncoder.encode(dto.getKeyword(), "UTF-8");

                String apiUrl = "https://apis.data.go.kr/B551011/KorService1/" +
                        "searchKeyword1?" +
                        "serviceKey=" + serviceKey +
                        "&numOfRows=" + dto.getNumOfRows() +
                        "&pageNo=" + dto.getPage() +
                        "&MobileOS=" + dto.getMobileOS() +
                        "&MobileApp=AppTest" +
                        "&_type=json" +
                        "&listYN=Y" +
                        "&arrange=A" +
                        "&keyword=" + encodedKeyword +
                        "&contentTypeId=" + dto.getContentTypeId();

                String responseJson = sendHttpRequest(apiUrl);
                List<OpenApiAreaDTO.Response.Body.Items.Item> responseList = new ArrayList<>();


                JSONObject jsonResponse = new JSONObject(responseJson);
                JSONObject responseBody = jsonResponse.getJSONObject("response").getJSONObject("body");
                JSONObject items = responseBody.getJSONObject("items");
                JSONArray itemList = items.getJSONArray("item");

                for (int i = 0; i < itemList.length(); i++) {
                    JSONObject item = itemList.getJSONObject(i);
                    String title = item.getString("title");
                    String firstImage = item.optString("firstimage", "");
                    String contentId = item.getString("contentid");
                    String contentTypeId = item.getString("contenttypeid");

                    OpenApiAreaDTO.Response.Body.Items.Item reponse = new OpenApiAreaDTO.Response.Body.Items.Item(title,firstImage,contentId,contentTypeId);
                    responseList.add(reponse);
                }
                return responseList;
            }catch (Exception e){
                return new ArrayList<>();
            }

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
            String apiKey = "/l8EmJ0xgdxUdyZT74sfz8sg9y9K9f3Yy8r3SbZYtFc";

            String apiUrl = "https://api.odsay.com/v1/api/searchPubTransPathT?" +
                    "SX=" + req.getSx() +
                    "&SY=" + req.getSy() +
                    "&EX=" + req.getEx() +
                    "&EY=" + req.getEy() +
                    "&SearchPathType=" + req.getSearchPathType() +
                    "&apiKey=" + URLEncoder.encode(apiKey, "UTF-8");

            String response = sendHttpRequest(apiUrl);

            log.info("리스폰 = " + response);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode resultNode = rootNode.get("result");
            if (resultNode != null) {
                JsonNode pathNode = resultNode.get("path");
                if (pathNode != null && pathNode.isArray() && pathNode.size() > 0) {
                    int totalTime = pathNode.get(0).get("info").get("totalTime").asInt();
                    log.info(String.valueOf(totalTime));
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
