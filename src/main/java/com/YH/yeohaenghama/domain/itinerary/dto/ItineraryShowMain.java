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
        private Long accountId;
        private Long itineraryId;
    }

    @Data
    public static class Response{
        private Long itineraryId;
        private Long dDay;
        private Map<String, Map<String, Object>> place;
    }

}
