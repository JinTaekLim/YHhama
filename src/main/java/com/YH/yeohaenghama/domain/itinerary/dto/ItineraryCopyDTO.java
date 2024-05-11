package com.YH.yeohaenghama.domain.itinerary.dto;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.budget.dto.BudgetCreateDTO;
import com.YH.yeohaenghama.domain.budget.entity.Budget;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class ItineraryCopyDTO {
    @Data @Schema(name = "ItineraryCopyDTO_Request")
    public static class Request{
        private Long accountId;
        private Long itineraryId;
    }


    @Data
    public static class itinerary{
        private Long id;
        private String name;    // 일정 제목
        private List<String> type;    // 누구와 여행하는지
        private List<String> itineraryStyle;    // 여행 스타일
        private Account account; // 일정을 제작한 유저 번호
        private String transportation = "bus";      // 교통 수단
        private String area;      // 출발지
        private LocalDate startDate;
        private LocalDate endDate;
//        private List<Place> places;

        public static ItineraryCopyDTO.itinerary fromEntity(Itinerary itinerary,Account account) {
            ItineraryCopyDTO.itinerary request = new ItineraryCopyDTO.itinerary();
            request.setId(itinerary.getId());
            request.setName(itinerary.getName());
            request.setType(new ArrayList<>(itinerary.getType()));
            request.setItineraryStyle(new ArrayList<>(itinerary.getItineraryStyle()));

//            List<Place> placeList = new ArrayList<>();
//            for(Place place : placeList){
//                place.setItinerary(itinerary);
//                placeList.add(place);
//            }
//
//            request.setPlaces(placeList);
            request.setTransportation(itinerary.getTransportation());
            request.setArea(itinerary.getArea());
            request.setStartDate(itinerary.getStartDate());
            request.setEndDate(itinerary.getEndDate());
            request.setAccount(account);
            return request;
        }


    }

    public static ItineraryCopyDTO.itinerary copyItinerary = new ItineraryCopyDTO.itinerary();
    public static Itinerary toEntity(ItineraryCopyDTO.itinerary copyItinerary){
        Itinerary itinerary = Itinerary.builder()
                .name(copyItinerary.getName())
                .type(copyItinerary.getType())
                .itineraryStyle(copyItinerary.getItineraryStyle())
                .transportation(copyItinerary.getTransportation())
                .area(copyItinerary.getArea())
                .startDate(copyItinerary.getStartDate())
                .endDate(copyItinerary.getEndDate())
                .places(new ArrayList<>())
                .build();
        itinerary.setAccount(copyItinerary.getAccount());
        return itinerary;
    }
}
