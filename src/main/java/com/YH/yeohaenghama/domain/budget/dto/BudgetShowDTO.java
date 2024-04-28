package com.YH.yeohaenghama.domain.budget.dto;

import com.YH.yeohaenghama.domain.budget.entity.Budget;
import com.YH.yeohaenghama.domain.budget.entity.Expenditures;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

public class BudgetShowDTO {
    @Data @Schema(name = "BudgetShowDTO_Request")
    public static class Request{
        @Schema(description = "일정 ID")
        private Long itinerary;
    }

    @Data
    @Slf4j

    public static class Response{
        private Long budgetId;
        private Integer totalAmount;
        private Long itineraryId;
        private List<ExpendituresShowDTO.Response> expendituresList = null;

        public static BudgetShowDTO.Response fromEntity(Budget budget){
            BudgetShowDTO.Response response = new BudgetShowDTO.Response();

            response.setBudgetId(budget.getId());
            response.setTotalAmount(budget.getTotalAmount());
            response.setItineraryId(budget.getItinerary().getId());

            return response;
        }

        public List<ExpendituresShowDTO.Response> setExpenditures(List<Expenditures> expendituresList){
            List<ExpendituresShowDTO.Response> response = new ArrayList<>();
            for(Expenditures expenditures : expendituresList){
                response.add(ExpendituresShowDTO.Response.fromEntity(expenditures));
            }

            this.setExpendituresList(response);
            return response;

        }
    }
}
