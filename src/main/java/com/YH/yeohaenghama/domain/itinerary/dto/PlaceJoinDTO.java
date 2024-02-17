package com.YH.yeohaenghama.domain.itinerary.dto;

import lombok.Data;

@Data
public class PlaceJoinDTO {
    private String placeNum;
    private String placeName;

    @Data
    public static class Request {
        private String placeNum;
        private String placeName;
    }
}
