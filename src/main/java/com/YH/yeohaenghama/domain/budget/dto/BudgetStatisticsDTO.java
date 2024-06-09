package com.YH.yeohaenghama.domain.budget.dto;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.budget.entity.Expenditures;
import com.YH.yeohaenghama.domain.budget.entity.ExpendituresGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class BudgetStatisticsDTO {
    @Schema(name = "BudgetStatisticsDTO_Request") @Data
    public static class Request{
        private Long budgetId;
        private Long accountId;
    }

    @Schema(name = "BudgetStatisticsDTO_Response") @Data
    public static class Response{
        private sortation budget;
        private sortation individual;

        public static Response toEntity(List<Expenditures> expendituresList, Account account){
            Response response = new Response();
            List<Expenditures> individualExpendituresList = new ArrayList<>();

            for(Expenditures expenditures : expendituresList){
                for(ExpendituresGroup expendituresGroup : expenditures.getExpendituresGroups()){
                    if(expendituresGroup.getAccount() == account) individualExpendituresList.add(expenditures);
                }
            }

            response.setBudget(getRatio(expendituresList));
            response.setIndividual(getRatio(individualExpendituresList));

            return response;
        }
        @Data
        public static class sortation{
            private BudgetStatisticsDTO.Ratio category;
            private BudgetStatisticsDTO.Ratio amount;

        }


        public static sortation getRatio(List<Expenditures> expendituresList){
            sortation response = new sortation();

            int[] ratioCategory = {0,0,0,0,0,0,0};
            int[] ratioAmount = {0,0,0,0,0,0,0};

            for(Expenditures expenditures : expendituresList){

                if (expenditures.getCategory().equals("lodging")) {
                    ratioCategory[0] += 1;
                    ratioAmount[0] += expenditures.getTotalAmount();
                }
                else if (expenditures.getCategory().equals("flight")) {
                    ratioCategory[1] += 1;
                    ratioAmount[1] += expenditures.getTotalAmount();
                }
                else if (expenditures.getCategory().equals("traffic")) {
                    ratioCategory[2] += 1;
                    ratioAmount[2] += expenditures.getTotalAmount();
                }
                else if (expenditures.getCategory().equals("tour")) {
                    ratioCategory[3] += 1;
                    ratioAmount[3] += expenditures.getTotalAmount();
                }
                else if (expenditures.getCategory().equals("food")) {
                    ratioCategory[4] += 1;
                    ratioAmount[4] += expenditures.getTotalAmount();
                }
                else if (expenditures.getCategory().equals("shopping")) {
                    ratioCategory[5] += 1;
                    ratioAmount[5] += expenditures.getTotalAmount();
                }
                else if (expenditures.getCategory().equals("other")) {
                    ratioCategory[6] += 1;
                    ratioAmount[6] += expenditures.getTotalAmount();
                }
            }

            response.setCategory(getPercentage(ratioCategory));
            response.setAmount(getPercentage(ratioAmount));

            return response;
        }
    }

    public static BudgetStatisticsDTO.Ratio getPercentage(int[] ratioList){
        BudgetStatisticsDTO.Ratio response = new Ratio();

        int sum = 0;

        for (int i : ratioList) sum += i;

        double ratio = 100.0 / sum;

        DecimalFormat df = new DecimalFormat("#.#");

        response.setLodging(Double.parseDouble(df.format(ratio * ratioList[0])));
        response.setFlight(Double.parseDouble(df.format(ratio * ratioList[1])));
        response.setTraffic(Double.parseDouble(df.format(ratio * ratioList[2])));
        response.setTour(Double.parseDouble(df.format(ratio * ratioList[3])));
        response.setFood(Double.parseDouble(df.format(ratio * ratioList[4])));
        response.setShopping(Double.parseDouble(df.format(ratio * ratioList[5])));
        response.setOther(Double.parseDouble(df.format(ratio * ratioList[6])));

        return response;
    }

    @Data
    public static class Ratio{
        private double lodging;
        private double flight;
        private double traffic;
        private double tour;
        private double food;
        private double shopping;
        private double other;
    }
}
