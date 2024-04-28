package com.YH.yeohaenghama.domain.budget.dto;

import com.YH.yeohaenghama.domain.budget.entity.Budget;
import com.YH.yeohaenghama.domain.budget.entity.Expenditures;
import com.YH.yeohaenghama.domain.itinerary.dto.PlaceShowExpendituresDTO;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

public class ExpendituresShowDTO {
    @Data @Schema(name = "ExpendituresShowDTO_Request")
    public static class Request{
        private Long id;
    }
    @Data
    public static class Response{
        private Long id;
        private PlaceShowExpendituresDTO.Response place = null;
        private Integer day;
        private String paymentMethod;
        private String category;
        private String name;
        private Integer amount;

        public static ExpendituresShowDTO.Response fromEntity(Expenditures expenditures){
            ExpendituresShowDTO.Response response = new ExpendituresShowDTO.Response();
            response.setId(expenditures.getId());
            response.setDay(expenditures.getDay());
            if(expenditures.getPlace() != null) {
                response.setPlace(PlaceShowExpendituresDTO.Response.fromEntity(expenditures.getPlace()));
                response.setDay(expenditures.getPlace().getDay());
            }

            response.setPaymentMethod(expenditures.getPaymentMethod());
            response.setCategory(expenditures.getCategory());
            response.setName(expenditures.getName());
            response.setAmount(expenditures.getAmount());

            return response;
        }


    }
}
