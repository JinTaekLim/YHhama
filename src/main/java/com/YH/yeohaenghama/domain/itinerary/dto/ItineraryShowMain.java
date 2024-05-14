package com.YH.yeohaenghama.domain.itinerary.dto;


import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import com.YH.yeohaenghama.domain.openApi.dto.OpenApiDirectionsDTO;
import com.YH.yeohaenghama.domain.openApi.service.OpenApiService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

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
//        private Map<String, Map<String, Object>> place;
        private Map<String, Map<String,List<Map<String,Object>>>> place;

        public void insertItinerary(Itinerary itinerary){
            this.itineraryId = itinerary.getId();
            this.place = insertPlace(itinerary);
            this.dDay = insertDday(itinerary);
        }

        public Map<String, Map<String,List<Map<String,Object>>>> insertPlace(Itinerary itinerary){
            Map<String, Map<String,List<Map<String,Object>>>> response = new HashMap<>();

            int day = 1;


            for(int i=0; i<itinerary.getPlaces().size(); i++) {

                Map<String, List<Map<String, Object>>> places = new HashMap<>();

                places.put("startPlace", getPlace(itinerary.getPlaces().get(i)));
                if(i+1 < itinerary.getPlaces().size()) {
                    places.put("endPlace", getPlace(itinerary.getPlaces().get(i+1)));
                    places.put("transportation", transportation(
                            itinerary.getPlaces().get(i).getMapx()
                            ,itinerary.getPlaces().get(i).getMapy()
                            ,itinerary.getPlaces().get(i+1).getMapx()
                            ,itinerary.getPlaces().get(i+1).getMapy()));

                }


//                Long startX = (long) place.getMapx();
//                Long endY = (long) place.getMapy();
//                places.put("Transportation", transportation(startX,endY,startX,endY));

                response.put(String.valueOf(day), places);
                day++;
            }

            return response;
        }

        public Long insertDday(Itinerary itinerary){
            LocalDate currentDate = LocalDate.now();
            LocalDate savedDate = itinerary.getStartDate();
            return ChronoUnit.DAYS.between(currentDate,savedDate);
        }

        public List<Map<String,Object>> getPlace(Place place){
            List<Map<String,Object>> show = new ArrayList<>();
            Map<String,Object> detail = new HashMap<>();

            detail.put("name", place.getPlaceName());
            detail.put("place_num", place.getPlaceNum());
            detail.put("place_type", place.getPlaceType());
            detail.put("startTime", place.getStartTime());
            detail.put("endTime", place.getEndTime());

            show.add(detail);

            return show;
        }

        @JsonIgnore
        private final OpenApiService openApiService;

        public List<Map<String,Object>> transportation(double startX,double startY, double endX, double endY){

            List<Map<String,Object>> show = new ArrayList<>();
            Map<String,Object> detail = new HashMap<>();
            OpenApiDirectionsDTO openApiDirectionsDTO = new OpenApiDirectionsDTO();
            openApiDirectionsDTO.setSx(String.valueOf(startX));
            openApiDirectionsDTO.setSy(String.valueOf(startY));
            openApiDirectionsDTO.setEx(String.valueOf(endX));
            openApiDirectionsDTO.setEy(String.valueOf(endY));

            detail.put("time", openApiService.getDirectionsTransport(openApiDirectionsDTO));

            show.add(detail);

            return show;
        }
    }

}
