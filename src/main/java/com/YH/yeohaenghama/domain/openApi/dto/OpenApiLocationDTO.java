package com.YH.yeohaenghama.domain.openApi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data @Schema(name = "OpenApiLocationDTO")
public class OpenApiLocationDTO {

    @Schema(description = "가져올 결과 수")
    private String numOfRows;
    @Schema(description = "페이지 번호", defaultValue = "1")
    private String page;
    @Schema(description = "OS 구분 : IOS (아이폰), AND (안드로이드), WIN (윈도우폰), ETC(기타)")
    private String MobileOS;
    @Schema(description = "X좌표")
    private String mapX;
    @Schema(description = "Y좌표")
    private String mapY;
    @Schema(description = "거리반경(단위:m) , Max값 20000m=20Km")
    private Integer radius;
    @Schema(description = "관광타입(12:관광지, 14:문화시설, 15:축제공연행사, 25:여행코스, 28:레포츠, 32:숙박, 38:쇼핑, 39:음식점) ID")
    private String contentTypeId;

}
