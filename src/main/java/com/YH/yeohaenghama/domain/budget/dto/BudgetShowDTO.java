package com.YH.yeohaenghama.domain.budget.dto;

import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.budget.entity.Budget;
import com.YH.yeohaenghama.domain.budget.entity.Expenditures;
import com.YH.yeohaenghama.domain.budget.entity.ExpendituresGroup;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.review.dto.ReviewDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

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
        private Integer budgetTotalAmount;
        private Integer totalAmount;
        private Long itineraryId;
//        private List<ExpendituresShowDTO.Response> expenditures;
        private Map<String,List<ExpendituresShowDTO.Response>> expenditures;
        public static Response fromEntity(Long accountId, List<Expenditures> expendituresList){
            BudgetShowDTO.Response response = new Response();
            Budget budget = expendituresList.get(0).getBudget();
            response.setBudgetId(budget.getId());
            response.setBudgetTotalAmount(getBudgetTotalAmount(budget));
            response.setItineraryId(budget.getItinerary().getId());
            response.setExpenditures(BudgetShowDTO.getExpenditures(accountId, budget.getExpenditures()));
            response.setTotalAmount(BudgetShowDTO.totalAmount);
            BudgetShowDTO.totalAmount = 0;
            return response;
        }

        public static Response fromEntity(Budget budget){
            BudgetShowDTO.Response response = new Response();
            response.setBudgetId(budget.getId());
            response.setItineraryId(budget.getItinerary().getId());
            response.setExpenditures(getDayResponse(budget.getItinerary(),new ArrayList<>()));
            response.setTotalAmount(BudgetShowDTO.totalAmount);
            return response;
        }

        public static Integer getBudgetTotalAmount(Budget budget){
            int response = 0;

            for(Expenditures expenditures : budget.getExpenditures()){
                response += expenditures.getTotalAmount();
            }

            return response;
        }
    }

    public static Map<String,List<ExpendituresShowDTO.Response>> getExpenditures(Long accountId, List<Expenditures> expendituresList){
        List<ExpendituresShowDTO.Response> expendituresDTO = new ArrayList<>();

        for (Expenditures expenditures : expendituresList) {
            for (ExpendituresGroup expendituresGroup : expenditures.getExpendituresGroups()) {
                if (expendituresGroup.getAccount().getId().equals(accountId)) {
                    totalAmount += expendituresGroup.getAmount();
                    expendituresDTO.add(ExpendituresShowDTO.Response.fromEntity(expenditures));
                }
            }
        }




        return getDayResponse(expendituresList.get(0).getBudget().getItinerary(), expendituresDTO);
    }

    public static Map<String,List<ExpendituresShowDTO.Response>> getDayResponse(Itinerary itinerary, List<ExpendituresShowDTO.Response> expendituresDTO){
        Map<String,List<ExpendituresShowDTO.Response>> response = new HashMap<>();

        Integer daysBetween = (int) (ChronoUnit.DAYS.between(itinerary.getStartDate(), itinerary.getEndDate()));

        for(int i=1; i<daysBetween+2; i++){
            List<ExpendituresShowDTO.Response> expendituresDay = new ArrayList<>();

            if(expendituresDTO != null) {
                for (ExpendituresShowDTO.Response expenditures : expendituresDTO) {
                    if (i == expenditures.getDay()) {
                        expendituresDay.add(expenditures);
                    }
                }
            }
            response.put(String.valueOf(i),expendituresDay);
        }

        return response;
    }
}
