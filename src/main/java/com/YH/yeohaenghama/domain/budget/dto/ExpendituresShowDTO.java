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

import java.util.List;

@Data
public class ExpendituresShowDTO {
    @Data
    public static class Response{
        private Long id;
//        private AccountShowDTO.Response account;
        private Integer expendituresTotalAmount;
        private boolean divided;
        private PlaceShowExpendituresDTO.Response place = null;
        private Integer day;
        private String content;
        private String paymentMethod;
        private String category;
        private boolean individual;
        private List<ExpendituresGroupShowDTO.Response> calculate;

        public static ExpendituresShowDTO.Response fromEntity(Expenditures expenditures){
            ExpendituresShowDTO.Response response = new ExpendituresShowDTO.Response();
            response.setId(expenditures.getId());
            response.setDivided(expenditures.isDivided());
//            response.setAccount(accountShow(expenditures.getAccount()));
            response.setDay(expenditures.getDay());
            if(expenditures.getPlace() != null) {
                response.setPlace(PlaceShowExpendituresDTO.Response.fromEntity(expenditures.getPlace()));
                response.setDay(expenditures.getPlace().getDay());
            }
            response.setIndividual(expenditures.isIndividual());
            response.setContent(expenditures.getContent());
            response.setPaymentMethod(expenditures.getPaymentMethod());
            response.setCategory(expenditures.getCategory());


            response.setCalculate(ExpendituresGroupShowDTO.Response.calculate(expenditures.getExpendituresGroups()));
            response.setExpendituresTotalAmount(ExpendituresGroupShowDTO.totalAmount);

            return response;
        }


    }

//    public static AccountShowDTO.Response accountShow(Account account){
//        AccountShowDTO.Response response = new AccountShowDTO.Response(account.getId(), account.getNickname(), account.getPhotoUrl(), account.getRole());
//        return response;
//    }
}
