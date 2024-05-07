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
        @Schema(description = "일정 ID")
        private Long itinerary;
    }

    @Data
    @Slf4j

    public static class Response{
        private Long budgetId;
        private Integer totalAmount;
        private Long itineraryId;
        private Map<String,List<ExpendituresShowDTO.Response>> expendituresList = null;

        public static BudgetShowDTO.Response fromEntity(Budget budget){
            BudgetShowDTO.Response response = new BudgetShowDTO.Response();

            response.setBudgetId(budget.getId());
            response.setTotalAmount(budget.getTotalAmount());
            response.setItineraryId(budget.getItinerary().getId());

            return response;
        }

        public Map<String,List<ExpendituresShowDTO.Response>> setExpenditures(Budget budget, List<Expenditures> expendituresList){

            Map<String,List<ExpendituresShowDTO.Response>> response = new LinkedHashMap();

            LocalDate startDate = budget.getItinerary().getStartDate();
            LocalDate endDate = budget.getItinerary().getEndDate();

            long numOfDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;

            for (int i = 1; i <= numOfDays; i++) {
                List<ExpendituresShowDTO.Response> expendituresResponse = new ArrayList<>();
                String dayKey = "Day-" + i;

                for(Expenditures expenditures : expendituresList){
                    if(expenditures.getDay() == i) expendituresResponse.add(ExpendituresShowDTO.Response.fromEntity(expenditures));
                }
                if( expendituresResponse != null) response.put(dayKey,expendituresResponse);
            }


            this.setExpendituresList(response);


            return response;

        }
    }
}
