package com.YH.yeohaenghama.domain.itinerary.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ItineraryShowMain {
    @Data
    @Schema(name = "ItineraryShowMain_Request")
    public static class Request {
        @Schema(description = "유저 ID")
        private Long accountId;
        @Schema(description = "일정 ID")
        private Long itineraryId;
    }

    @Data
    public static class Response{
        private Long itineraryId;
        private Long dDay;
        private Map<String, Map<String, Object>> place;
    }

}
