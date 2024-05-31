package com.YH.yeohaenghama.domain.budget.dto;

import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.budget.entity.Expenditures;
import com.YH.yeohaenghama.domain.budget.entity.ExpendituresGroup;
import com.YH.yeohaenghama.domain.itinerary.dto.PlaceShowExpendituresDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class calculateGroupDTO {
    @Data @Schema(name = "calculateGroupDTO_Response")
    public static class Response{
        private Long id;
        private PlaceShowExpendituresDTO.Response place = null;
        private Integer day;
        private String content;
        private String paymentMethod;
        private String category;
        private String name;
        private Integer amount;

        public static calculateGroupDTO.Response fromEntity(ExpendituresGroup expendituresGroup){
            calculateGroupDTO.Response response = new calculateGroupDTO.Response();
            response.setId(expendituresGroup.getId());

            response.setDay(expendituresGroup.getDay());
            if(expendituresGroup.getPlace() != null) {
                response.setPlace(PlaceShowExpendituresDTO.Response.fromEntity(expendituresGroup.getPlace()));
                response.setDay(expendituresGroup.getPlace().getDay());
            }

            response.setContent(expendituresGroup.getContent());
            response.setPaymentMethod(expendituresGroup.getPaymentMethod());
            response.setCategory(expendituresGroup.getCategory());
            response.setName(expendituresGroup.getName());
            response.setAmount(expendituresGroup.getAmount());

            return response;
        }


    }
}
