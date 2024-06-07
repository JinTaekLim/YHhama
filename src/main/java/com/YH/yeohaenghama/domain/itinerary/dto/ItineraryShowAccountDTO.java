package com.YH.yeohaenghama.domain.itinerary.dto;

import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

public class ItineraryShowAccountDTO {

    @Data
    @Schema(description = "ItineraryShowAccountDTO_Response")
    public static class Response{
        private Long id;
        private String name;
        private String area;
        private LocalDate startDate;
        private LocalDate endDate;
        private Integer placeLength;
        private Integer sharedAccount;
        private boolean diary;


        public static ItineraryShowAccountDTO.Response fromEntity(Itinerary itinerary,boolean diary) {
            ItineraryShowAccountDTO.Response response = new ItineraryShowAccountDTO.Response();
            response.setId(itinerary.getId());
            response.setName(itinerary.getName());
            response.setArea(itinerary.getArea());
            response.setStartDate(itinerary.getStartDate());
            response.setEndDate(itinerary.getEndDate());
            response.setPlaceLength(itinerary.getPlaces().size());
            response.setSharedAccount(itinerary.getJoinAccount().size() - 1);
            if(itinerary.getJoinAccount().size() == 0) response.setSharedAccount(0);
            response.setDiary(diary);
            return response;
        }
    }
}
