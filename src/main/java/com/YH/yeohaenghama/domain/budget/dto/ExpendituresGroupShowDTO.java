package com.YH.yeohaenghama.domain.budget.dto;

import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.budget.entity.Expenditures;
import com.YH.yeohaenghama.domain.budget.entity.ExpendituresGroup;
import com.YH.yeohaenghama.domain.itinerary.dto.PlaceShowExpendituresDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class ExpendituresGroupShowDTO {
    @Data
    @Schema(name = "ExpendituresGroupShowDTO_Request")
    public static class Request{
        private Long id;
    }
    @Data
    public static class Response{
        private Long id;
        private AccountShowDTO.Response accountShowDTO;
        private PlaceShowExpendituresDTO.Response place = null;
        private Integer day;
        private String paymentMethod;
        private String category;
        private String name;
        private Integer amount;

        public static ExpendituresGroupShowDTO.Response fromEntity(ExpendituresGroup expendituresGroup){
            ExpendituresGroupShowDTO.Response response = new ExpendituresGroupShowDTO.Response();
            response.setId(expendituresGroup.getId());

            Account account = expendituresGroup.getItineraryJoinAccount().getAccount();
            AccountShowDTO.Response accountShowDTO = new AccountShowDTO.Response(account.getId(), account.getNickname(), account.getPhotoUrl(), account.getRole());
            response.setAccountShowDTO(accountShowDTO);

            response.setDay(expendituresGroup.getDay());
            if(expendituresGroup.getPlace() != null) {
                response.setPlace(PlaceShowExpendituresDTO.Response.fromEntity(expendituresGroup.getPlace()));
                response.setDay(expendituresGroup.getPlace().getDay());
            }

            response.setPaymentMethod(expendituresGroup.getPaymentMethod());
            response.setCategory(expendituresGroup.getCategory());
            response.setName(expendituresGroup.getName());
            response.setAmount(expendituresGroup.getAmount());

            return response;
        }


    }
}
