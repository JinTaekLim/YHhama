package com.YH.yeohaenghama.domain.itinerary.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class ItineraryDeletePlaceDTO {

    @Data
    @Schema(name = "일정 장소 개별 삭제")
    public static class Request{
        @Schema(description = "일정ID")
        private Long itineraryId;
        @Schema(description = "장소ID")
        private Long placeId;

    }
}
