package com.YH.yeohaenghama.domain.budget.dto;

import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.budget.entity.Budget;
import com.YH.yeohaenghama.domain.budget.entity.Expenditures;
import com.YH.yeohaenghama.domain.itinerary.dto.PlaceShowExpendituresDTO;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ExpendituresAddDTO {

    private Place place;

    @Data @Schema(name = "ExpendituresAddDTO_Request")
    public static class Request{
        private Long budgetId;
        private Long accountId;
        private Long place = null;
        private Integer day = null;
        private String paymentMethod;
        private String content;
        private String category;
        private Integer amount;
        private boolean individual;
    }
    @Data @Schema(name = "ExpendituresAddDTO_Response")
    public static class Response{
        private Long id;
        private AccountShowDTO.Response account;
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
        private boolean individual;
        public static ExpendituresAddDTO.Response fromEntity(Expenditures expenditures){
            ExpendituresAddDTO.Response response = new ExpendituresAddDTO.Response();
            response.setId(expenditures.getId());
            response.setAccount(accountShow(expenditures.getAccount()));
            response.setBudget(expenditures.getBudget());
//            if(place != null){
//                response.setPlaceEntity(place);
//                PlaceShowExpendituresDTO.Response placeResponse = PlaceShowExpendituresDTO.Response.fromEntity(place);
//                response.setPlace(placeResponse);
//            }
            response.setContent(expenditures.getContent());
            response.setPaymentMethod(expenditures.getPaymentMethod());
            response.setCategory(expenditures.getCategory());
            response.setAmount(expenditures.getAmount());
            response.setIndividual(expenditures.isIndividual());

            return response;
        }
//        public ExpendituresAddDTO.Response setPlace(Place place) {
//            this.placeEntity = place;
//            PlaceShowExpendituresDTO.Response placeResponse = PlaceShowExpendituresDTO.Response.fromEntity(place);
//            this.place = placeResponse;
//            return this;
//        }


    }

    private ExpendituresAddDTO.Request request;
    private ExpendituresAddDTO.Response response;

    public ExpendituresAddDTO(Request request) {
        this.request = request;
    }

//    public ExpendituresAddDTO(Response response) {
//        this.response = response;
//    }


    public Expenditures toEntity(Account account, Budget budget) {
        Expenditures.ExpendituresBuilder expendituresBuilder = Expenditures.builder()
                .account(account)
                .budget(budget)
                .day(request.getDay())
                .paymentMethod(request.getPaymentMethod())
                .content(request.getContent())
                .category(request.getCategory())
                .amount(request.getAmount())
                .individual(request.isIndividual());

        if (place != null) {
            expendituresBuilder.place(place);
        }

        return expendituresBuilder.build();
    }



    public static AccountShowDTO.Response accountShow(Account account){
        AccountShowDTO.Response response = new AccountShowDTO.Response(account.getId(), account.getNickname(), account.getPhotoUrl(), account.getRole());
        return response;
    }
}
