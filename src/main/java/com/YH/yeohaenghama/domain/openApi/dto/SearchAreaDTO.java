package com.YH.yeohaenghama.domain.openApi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class SearchAreaDTO {
    @Schema(name = "SearchAreaDTO_Request") @Data
    public static class Reqeust{
        @Schema(description = "가져올 결과 수")
        private String numOfRows;
        @Schema(description = "페이지 번호", defaultValue = "1")
        private String page;
        @Schema(description = "OS 구분 : IOS (아이폰), AND (안드로이드), WIN (윈도우폰), ETC(기타)")
        private String MobileOS;
        @Schema(description = "지역 이름 ( 2글자 ) ex: 인천,서울,강원 ")
        private String keyword;
        @Schema(description = "관광타입(12:관광지, 14:문화시설, 15:축제공연행사, 25:여행코스, 28:레포츠, 32:숙박, 38:쇼핑, 39:음식점) ID")
        private String contentTypeId;
    }

    @Schema(name = "SearchAreaDTO_Response") @Data
    public static class Response {
        @JsonProperty("numOfRows")
        private int numOfRows;
        @JsonProperty("pageNo")
        private int pageNo;
        @JsonProperty("totalCount")
        private int totalCount;
        private List<info> place;


        public static Response parsing(String response) throws JsonProcessingException {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);



            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode bodyNode = rootNode.path("response").path("body");


            SearchAreaDTO.Response dtoResponse = new SearchAreaDTO.Response();
            dtoResponse.setNumOfRows(bodyNode.path("numOfRows").asInt());
            dtoResponse.setPageNo(bodyNode.path("pageNo").asInt());
            dtoResponse.setTotalCount(bodyNode.path("totalCount").asInt());

            List<SearchAreaDTO.info> places = new ArrayList<>();
            JsonNode itemsNode = bodyNode.path("items").path("item");
            for (JsonNode itemNode : itemsNode) {
                SearchAreaDTO.info placeInfo = objectMapper.treeToValue(itemNode, SearchAreaDTO.info.class);
                places.add(placeInfo);
            }
            dtoResponse.setPlace(places);

            return dtoResponse;
        }
    }

    @Data
    public static class info{
        @JsonProperty("addr1")
        private String addr1;
        @JsonProperty("addr2")
        private String addr2;
        @JsonProperty("contentid")
        private String contentid;
        @JsonProperty("contenttypeid")
        private String contenttypeid;
        @JsonProperty("tel")
        private String tel;
        @JsonProperty("title")
        private String title;
        @JsonProperty("firstimage")
        private String firstimage;
        @JsonProperty("firstimage2")
        private String firstimage2;
        @JsonProperty("mapx")
        private String mapx;
        @JsonProperty("mapy")
        private String mapy;
//        @JsonProperty("areacode")
//        private String areacode;
//        @JsonProperty("cpyrhtDivCd")
//        private String cpyrhtDivCd;
//        @JsonProperty("createdtime")
//        private String createdtime;
//        @JsonProperty("booktour")
//        private String booktour;
//        @JsonProperty("cat1")
//        private String cat1;
//        @JsonProperty("cat2")
//        private String cat2;
//        @JsonProperty("cat3")
//        private String cat3;
//        @JsonProperty("mlevel")
//        private String mlevel;
//        @JsonProperty("modifiedtime")
//        private String modifiedtime;
//        @JsonProperty("sigungucode")
//        private String sigungucode;
    }
}
