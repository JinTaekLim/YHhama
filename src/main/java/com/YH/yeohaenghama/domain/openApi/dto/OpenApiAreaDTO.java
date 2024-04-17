package com.YH.yeohaenghama.domain.openApi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OpenApiAreaDTO {

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

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Response {
        private Body body;

        @Data
        public static class Body {
            private Items items;

            @Data
            public static class Items {
                private List<Item> item;

                @Data
                public static class Item {
                    private String title;
                    private String firstimage;
                    private String contentid;
                    private String contenttypeid;

                    public Item(String title,String firstimage, String contentid, String contenttypeid){
                        this.title = title;
                        this.firstimage = firstimage;
                        this.contentid = contentid;
                        this.contenttypeid = contenttypeid;

                    }
                }
            }
        }
    }


}
