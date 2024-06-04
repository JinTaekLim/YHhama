package com.YH.yeohaenghama.domain.budget.dto;

import com.YH.yeohaenghama.domain.budget.entity.Budget;
import com.YH.yeohaenghama.domain.budget.entity.Expenditures;
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
        public static Response fromEntity(Budget budget, List<Expenditures> expendituresList){
            BudgetShowDTO.Response response = new Response();
            response.setBudgetId(budget.getId());
            response.setTotalAmount(0);
            response.setItineraryId(budget.getItinerary().getId());
            response.setExpenditures(BudgetShowDTO.getExpenditures(expendituresList));

            return response;
        }
    }

    public static List<ExpendituresShowDTO.Response> getExpenditures(List<Expenditures> expendituresList){
        List<ExpendituresShowDTO.Response> response = new ArrayList<>();

        for(Expenditures expenditures : expendituresList){
            response.add(ExpendituresShowDTO.Response.fromEntity(expenditures));
            System.out.println("a" + expenditures.getId());
        }
        return response;
    }
}
