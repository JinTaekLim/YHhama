package com.YH.yeohaenghama.domain.budget.dto;

import com.YH.yeohaenghama.domain.budget.entity.Budget;
import com.YH.yeohaenghama.domain.budget.entity.Expenditures;
import com.YH.yeohaenghama.domain.itinerary.dto.ItineraryJoinDTO;
import com.YH.yeohaenghama.domain.itinerary.dto.PlaceShowExpendituresDTO;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

public class ExpendituresAddDTO {
    @Data @Schema(name = "ExpendituresAddDTO_Request")
    public static class Request{
        private Long itineraryId;
        private Long place = null;
        private Integer day = null;
        private String paymentMethod;
        private String content;
        private String category;
        private String name;
        private Integer amount;
    }
    @Data @Schema(name = "ExpendituresAddDTO_Response")
    public static class Response{
        private Long id;
        @JsonIgnore
        private Budget budget;
        @JsonIgnore
        private Place placeEntity = null;
        private PlaceShowExpendituresDTO.Response place = null;
        private int day;
        private String content;
        private String paymentMethod;
        private String category;
        private String name;
        private Integer amount;

        public static ExpendituresAddDTO.Response fromEntity(Expenditures expenditures){
            ExpendituresAddDTO.Response response = new ExpendituresAddDTO.Response();
            response.setId(expenditures.getId());
            response.setBudget(expenditures.getBudget());
//            if(place != null){
//                response.setPlaceEntity(place);
//                PlaceShowExpendituresDTO.Response placeResponse = PlaceShowExpendituresDTO.Response.fromEntity(place);
//                response.setPlace(placeResponse);
//            }
            response.setContent(expenditures.getContent());
            response.setPaymentMethod(expenditures.getPaymentMethod());
            response.setCategory(expenditures.getCategory());
            response.setName(expenditures.getName());
            response.setAmount(expenditures.getAmount());

            return response;
        }
        public ExpendituresAddDTO.Response setPlace(Place place) {
            this.placeEntity = place;
            PlaceShowExpendituresDTO.Response placeResponse = PlaceShowExpendituresDTO.Response.fromEntity(place);
            this.place = placeResponse;
            return this;
        }


    }

    private ExpendituresAddDTO.Request request;
    private ExpendituresAddDTO.Response response;

    public ExpendituresAddDTO(Request request) {
        this.request = request;
    }

    public ExpendituresAddDTO(Response response) {
        this.response = response;
    }


    public Expenditures toEntity() {
        Expenditures expenditures = Expenditures.builder()
                .day(request.getDay())
                .paymentMethod(request.getPaymentMethod())
                .content(request.getContent())
                .category(request.getCategory())
                .name(request.getName())
                .amount(request.getAmount())
                .build();
        return expenditures;
    }
}
