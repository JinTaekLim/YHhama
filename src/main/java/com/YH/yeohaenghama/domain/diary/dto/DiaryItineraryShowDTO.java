package com.YH.yeohaenghama.domain.diary.dto;

import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import com.YH.yeohaenghama.domain.review.entity.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class DiaryItineraryShowDTO {
    @Data
    @Schema(description = "DiaryItineraryShowDTO_Response")
    public static class Response {
        @Schema(description = "일정 ID")
        private Long itineraryId;

        @Schema(description = "동반자(0:기타, 1:혼자, 2:친구와, 3:연인과, 4:배우자와,5:아이와,6:부모님과)")
        private List<String> type;
        @Schema(description = "일정 스타일(1:체험－엑티비티, 2:sns－핫플레이스, 3:자연과함께, 4:유명한 관광지는 필수, 5:여유롭게 힐링, 6:문화－예술－역사, 7:여행지 느낌 물씬, 8:쇼핑은 열정적으로, 9:관광보다 먹방)")
        private List<String> style;
        @Schema(description = "일정 이름")
        private String name;
        @Schema(description = "교통 수단(0:자동차, 1:지하철, 2:버스)")
        private String transportation;
        @Schema(description = "지역")
        private String area;
        @Schema(description = "시작 일시")
        private LocalDate startDate;
        @Schema(description = "종료 일시")
        private LocalDate endDate;
        @Schema(description = "해당 일정에 포함된 장소")
        private Map<String, List<DiaryShowPlaceDTO.Response>> place;

        public static Response fromEntity(Itinerary itinerary, List<Review> review) {
            Response response = new Response();
            response.setItineraryId(itinerary.getId());
            response.setType(itinerary.getType());
            response.setStyle(itinerary.getItineraryStyle());
            response.setName(itinerary.getName());
            response.setTransportation(itinerary.getTransportation());
            response.setArea(itinerary.getArea());
            response.setStartDate(itinerary.getStartDate());
            response.setEndDate(itinerary.getEndDate());
            response.setPlace(getPlace(itinerary,review));
            return response;
        }

        private static Map<String, List<DiaryShowPlaceDTO.Response>> getPlace(Itinerary itinerary,List<Review> review) {
            Map<String, List<DiaryShowPlaceDTO.Response>> response = new HashMap<>();

            long itineraryDay = ChronoUnit.DAYS.between(itinerary.getStartDate(), itinerary.getEndDate()) + 2;

            for (int i = 1; i < itineraryDay; i++) {
                String day = "Day-" + i;
                response.put(day, new ArrayList<>());
            }

            for (Place place : itinerary.getPlaces()) {
                String day = "Day-" + place.getDay();
                response.get(day).add(DiaryShowPlaceDTO.Response.fromEntity(place,review));
            }

            return response;
        }

    }
}
