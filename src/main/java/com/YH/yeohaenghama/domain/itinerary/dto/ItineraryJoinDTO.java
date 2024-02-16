package com.YH.yeohaenghama.domain.itinerary.dto;

import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import lombok.Data;

@Data
public class ItineraryJoinDTO {


    @Data
    public static class Request {
        private String name;
        private String account;
        private String transportation;
        private String startPlace;
        private String endPlace;
        private String startDate;
        private String endDate;
        private String expense;
    }

    @Data
    public static class Response {
        private String name;
        private String account;
        private String transportation;
        private String startPlace;
        private String endPlace;
        private String startDate;
        private String endDate;
        private String expense;

        public static Response fromEntity(Itinerary itinerary) {
            Response response = new Response();
            response.setName(itinerary.getName());
            response.setAccount(itinerary.getAccount());
            response.setTransportation(itinerary.getTransportation());
            response.setStartPlace(itinerary.getStartPlace());
            response.setEndPlace(itinerary.getEndPlace());
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
                .startPlace(request.getStartPlace())
                .endPlace(request.getEndPlace())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .expense(request.getExpense())
                .build();
        return itinerary;
    }

}
