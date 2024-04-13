package com.YH.yeohaenghama.domain.itinerary.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class ItineraryShowInPlaceDTO {

    @Schema(description = "ItineraryShowInPlaceDTO_Request")
    @Data
    public static class Request{
        @Schema(description = "장소 타입 번호")
        private String placeType;
        @Schema(description = "장소 번호(코드)")
        private String placeNum;
    }

    @Data
    public static class Response{

    }
}
