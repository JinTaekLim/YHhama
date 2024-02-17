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

    @Schema(description = "장소 번호(코드)")
    private String name;
    private String account;
    private String transportation;
    private String area;
    private String startDate;
    private String endDate;
    private String expense;


    @Data
    @Schema(name = "ItineraryRequestDTO")
    public static class Request {
        @Schema(description = "일정 이름")
        private String name;
        @Schema(description = "일정 제작한 계정")
        private String account;
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
    }

    @Data
    @Schema(name = "ItineraryReponseDTO")
    public static class Response {
        @Schema(description = "일정 이름")
        private String name;
        @Schema(description = "일정 제작한 계정")
        private String account;
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

        public static Response fromEntity(Itinerary itinerary) {
            Response response = new Response();
            response.setName(itinerary.getName());
            response.setAccount(itinerary.getAccount());
            response.setTransportation(itinerary.getTransportation());
            response.setArea(itinerary.getArea());
            response.setStartDate(itinerary.getStartDate());
            response.setEndDate(itinerary.getEndDate());
            response.setExpense(itinerary.getExpense());
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
                .account(request.getAccount())
                .transportation(request.getTransportation())
                .area(request.getArea())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .expense(request.getExpense())
                .build();
        return itinerary;
    }

}
