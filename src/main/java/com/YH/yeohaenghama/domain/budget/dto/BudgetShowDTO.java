package com.YH.yeohaenghama.domain.budget.dto;

import com.YH.yeohaenghama.domain.budget.entity.Budget;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class BudgetShowDTO {
    @Data @Schema(name = "BudgetShowDTO_Request")
    public static class Request{
        @Schema(description = "일정 ID")
        private Long itinerary;
    }

    @Data
    public static class Response{
        private Long budgetId;
        private Integer totalAmount;
        private Long itineraryId;

        public static BudgetShowDTO.Response fromEntity(Budget budget){
            BudgetShowDTO.Response response = new BudgetShowDTO.Response();

            response.setBudgetId(budget.getId());
            response.setTotalAmount(budget.getTotalAmount());
            response.setItineraryId(budget.getItinerary().getId());

            return response;
        }
    }
}
