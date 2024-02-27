package com.YH.yeohaenghama.domain.itinerary.dto;

import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ItineraryJoinDTO {

    @Schema(description = "일정 이름")
    private String name;
    @Schema(description = "누구와 함께하는지")
    private String type;
    @Schema(description = "교통 수단")
    private String transportation;
    @Schema(description = "여행 지역")
    private String area;
    @Schema(description = "일정 시작 일시")
    private String startDate;
    @Schema(description = "일정 종료 일시")
    private String endDate;
    @Schema(description = "경비 관리")
    private String expense;
    @Schema(description = "일정 타입들")
    private List<String> itineraryStyle;  // 이 부분이 수정된 부분입니다.

    @Data
    @Schema(name = "ItineraryRequestDTO")
    public static class Request {
        @Schema(description = "일정 이름")
        private String name;
        @Schema(description = "누구와 함께하는지")
        private String type;
        @Schema(description = "교통 수단")
        private String transportation;
        @Schema(description = "여행 지역")
        private String area;
        @Schema(description = "일정 시작 일시")
        private String startDate;
        @Schema(description = "일정 종료 일시")
        private String endDate;
        @Schema(description = "경비 관리")
        private String expense;
        @Schema(description = "일정 타입들")
        private List<String> itineraryStyle;
    }

    @Data
    @Schema(name = "ItineraryReponseDTO")
    public static class Response {
        @Schema(description = "일정 이름")
        private String name;
        @Schema(description = "누구와 함께하는지")
        private String type;
        @Schema(description = "교통 수단")
        private String transportation;
        @Schema(description = "여행 지역")
        private String area;
        @Schema(description = "일정 시작 일시")
        private String startDate;
        @Schema(description = "일정 종료 일시")
        private String endDate;
        @Schema(description = "경비 관리")
        private String expense;
        @Schema(description = "일정 타입들")
        private List<String> itineraryStyle;

        public static Response fromEntity(Itinerary itinerary) {
            Response response = new Response();
            response.setName(itinerary.getName());
            response.setType(itinerary.getType());
            response.setTransportation(itinerary.getTransportation());
            response.setArea(itinerary.getArea());
            response.setStartDate(itinerary.getStartDate());
            response.setEndDate(itinerary.getEndDate());
            response.setExpense(itinerary.getExpense());
            response.setItineraryStyle(itinerary.getItineraryStyle());
            return response;
        }
    }

    private Request request;
    private Response response;

    public ItineraryJoinDTO(Request request) {
        this.request = request;
    }

    public ItineraryJoinDTO(Response response) {
        this.response = response;
    }

    public Itinerary toEntity() {
        Itinerary itinerary = Itinerary.builder()
                .name(request.getName())
                .type(request.getType())
                .transportation(request.getTransportation())
                .area(request.getArea())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .expense(request.getExpense())
                .itineraryStyle(request.getItineraryStyle())
                .build();
        return itinerary;
    }
}
