package com.YH.yeohaenghama.domain.itinerary.dto;

import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import lombok.Data;

public class PlaceShowExpendituresDTO {
    @Data
    public static class Response{
        private Long id;
        private Integer day;
        private String placeName;
        private String placeNum;
        private String placeType;

        public static PlaceShowExpendituresDTO.Response fromEntity(Place place){
            PlaceShowExpendituresDTO.Response response = new PlaceShowExpendituresDTO.Response();
            response.setId(place.getId());
            response.setDay(place.getDay());
            response.setPlaceName(place.getPlaceName());
            response.setPlaceNum(place.getPlaceNum());
            response.setPlaceType(place.getPlaceType());
            return response;
        }
    }
}
