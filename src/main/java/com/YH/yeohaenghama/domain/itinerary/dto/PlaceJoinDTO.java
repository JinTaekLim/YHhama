package com.YH.yeohaenghama.domain.itinerary.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PlaceJoinDTO {
    @Schema(description = "지정 날짜")
    private int day;
    @Schema(description = "장소 타입 번호")
    private String placeType;
    @Schema(description = "장소 번호(코드)")
    private String placeNum;
    @Schema(description = "장소 이름")
    private String placeName;
    @Schema(description = "메모")
    private String memo;

    @Data
    public static class Request {
        private int day;
        private String placeType;
        private String placeNum;
        private String placeName;
        private String memo;
    }
}
