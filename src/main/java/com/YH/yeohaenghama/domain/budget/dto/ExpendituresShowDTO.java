package com.YH.yeohaenghama.domain.budget.dto;

import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.budget.entity.Budget;
import com.YH.yeohaenghama.domain.budget.entity.Expenditures;
import com.YH.yeohaenghama.domain.itinerary.dto.PlaceShowExpendituresDTO;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

public class ExpendituresShowDTO {
//    @Data @Schema(name = "ExpendituresShowDTO_Request")
//    public static class Request{
//        private Long BudgetId;
//        private Long accountId;
//    }
    @Data
    public static class Response{
        private Long id;
//        private AccountShowDTO.Response account;
        private PlaceShowExpendituresDTO.Response place = null;
        private Integer day;
        private String content;
        private String paymentMethod;
        private String category;
        private String name;
        private Integer amount;

        public static ExpendituresShowDTO.Response fromEntity(Expenditures expenditures){
            ExpendituresShowDTO.Response response = new ExpendituresShowDTO.Response();
            response.setId(expenditures.getId());
//            response.setAccount(accountShow(expenditures.getAccount()));
            response.setDay(expenditures.getDay());
            if(expenditures.getPlace() != null) {
                response.setPlace(PlaceShowExpendituresDTO.Response.fromEntity(expenditures.getPlace()));
                response.setDay(expenditures.getPlace().getDay());
            }

            response.setContent(expenditures.getContent());
            response.setPaymentMethod(expenditures.getPaymentMethod());
            response.setCategory(expenditures.getCategory());

            return response;
        }


    }

    public static AccountShowDTO.Response accountShow(Account account){
        AccountShowDTO.Response response = new AccountShowDTO.Response(account.getId(), account.getNickname(), account.getPhotoUrl(), account.getRole());
        return response;
    }
}
