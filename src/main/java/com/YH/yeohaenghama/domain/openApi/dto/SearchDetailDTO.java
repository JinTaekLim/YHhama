package com.YH.yeohaenghama.domain.openApi.dto;

import com.YH.yeohaenghama.domain.addPlace.entity.AddPlace;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class SearchDetailDTO {
    @Schema(name = "SearchDetailDTO_Request") @Data
    public static class Request{
        @Schema(description = "OS 구분 : IOS (아이폰), AND (안드로이드), WIN (윈도우폰), ETC(기타)")
        private String MobileOS;
        @Schema(description = "콘텐트ID")
        private String contentId;
        @Schema(description = "관광타입(12:관광지, 14:문화시설, 15:축제공연행사, 25:여행코스, 28:레포츠, 32:숙박, 38:쇼핑, 39:음식점) ID")
        private String contentTypeId;
        @Schema(description = "가져올 결과 수", defaultValue = "1")
        private String numOfRows;
        @Schema(description = "페이지 번호" , defaultValue = "1")
        private String pageNo;
    }
    @Schema(name = "SearchDetailDTO_Response") @Data
    public static class Response {
        @Schema(description = "컨텐츠 ID")
        @JsonProperty("contentid")
        private String contentId;

        @Schema(description = "컨텐츠 타입 ID")
        @JsonProperty("contenttypeid")
        private String contentTypeId;

        @Schema(description = "장소 이름")
        @JsonProperty("title")
        private String title;

        @Schema(description = "대표 이미지 URL")
        @JsonProperty("firstimage")
        private String firstImage;

        @Schema(description = "추가 이미지 URL")
        @JsonProperty("firstimage2")
        private String firstImage2;

        @Schema(description = "주소 1")
        @JsonProperty("addr1")
        private String addr1;

        @Schema(description = "주소 2")
        @JsonProperty("add2")
        private String addr2;

        @Schema(description = "지도 X 좌표")
        @JsonProperty("mapx")
        private String mapx;

        @Schema(description = "지도 Y 좌표")
        @JsonProperty("mapy")
        private String mapy;

        @Schema(description = "개요")
        @JsonProperty("overview")
        private String overview;

        public static SearchDetailDTO.Response parse(String jsonResponse) throws Exception {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode itemsNode = rootNode.path("response").path("body").path("items").path("item");

            SearchDetailDTO.Response response = new SearchDetailDTO.Response();
            if (itemsNode.isArray()) {
                JsonNode itemNode = itemsNode.get(0); 
                response.setContentId(itemNode.path("contentid").asText());
                response.setContentTypeId(itemNode.path("contenttypeid").asText());
                response.setTitle(itemNode.path("title").asText());
                response.setFirstImage(itemNode.path("firstimage").asText());
                response.setFirstImage2(itemNode.path("firstimage2").asText());
                response.setAddr1(itemNode.path("addr1").asText());
                response.setAddr2(itemNode.path("addr2").asText());
                response.setMapx(itemNode.path("mapx").asText());
                response.setMapy(itemNode.path("mapy").asText());
                response.setOverview(itemNode.path("overview").asText());
            }
            return response;
        }

        public static SearchDetailDTO.Response parse(AddPlace addPlace,String photoUrl){
            SearchDetailDTO.Response response = new SearchDetailDTO.Response();
            response.setContentId(String.valueOf(addPlace.getId()));
            response.setContentTypeId("80");
            response.setTitle(addPlace.getTitle());
            response.setAddr1(addPlace.getAdd1());
            response.setAddr2(addPlace.getAdd2());
            response.setMapx(addPlace.getMapX());
            response.setMapy(addPlace.getMapY());
            if(!photoUrl.equals("")) response.setFirstImage(photoUrl);
            return response;
        }
    }
}
