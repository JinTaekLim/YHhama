package com.YH.yeohaenghama.domain.budget.dto;

import com.YH.yeohaenghama.domain.budget.entity.Budget;
import com.YH.yeohaenghama.domain.budget.entity.Expenditures;
import com.YH.yeohaenghama.domain.budget.entity.ExpendituresGroup;
import com.YH.yeohaenghama.domain.review.dto.ReviewDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BudgetShowDTO {
    private static Integer totalAmount = 0;
    @Data @Schema(name = "BudgetShowDTO_Request")
    public static class Request{
        @Schema(description = "가계부 ID")
        private Long budgetId;
        @Schema(description = "유저 ID")
        private Long accountId;
    }

    @Data @Schema(name = "BudgetShowDTO_Response")
    public static class Response{
        private Long budgetId;
        private Integer totalAmount;
        private Long itineraryId;
        private List<ExpendituresShowDTO.Response> expenditures;
        public static Response fromEntity(Long accountId, List<Expenditures> expendituresList){
            BudgetShowDTO.Response response = new Response();
            Budget budget = expendituresList.get(0).getBudget();
            response.setBudgetId(budget.getId());
            response.setItineraryId(budget.getItinerary().getId());
            response.setExpenditures(BudgetShowDTO.getExpenditures(accountId, expendituresList));
            response.setTotalAmount(BudgetShowDTO.totalAmount);
            return response;
        }
    }

    public static List<ExpendituresShowDTO.Response> getExpenditures(Long accountId, List<Expenditures> expendituresList){
        List<ExpendituresShowDTO.Response> response = new ArrayList<>();

        for(Expenditures expenditures : expendituresList){

            for(ExpendituresGroup expendituresGroup : expenditures.getExpendituresGroups()){
                if(expendituresGroup.getAccount().getId().equals(accountId)){
                    totalAmount += expendituresGroup.getAmount();
                    response.add(ExpendituresShowDTO.Response.fromEntity(expenditures));
                }
            }
        }
        return response;
    }
}
