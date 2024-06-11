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

            int[][] ratioCategory = {{0,0,0,0,0,0,0},{0,0,0,0,0,0,0}};
            int[][] ratioAmount = {{0,0,0,0,0,0,0},{0,0,0,0,0,0,0}};

            for(Expenditures expenditures : expendituresList){

                if (expenditures.getCategory().equals("lodging")) {
                    ratioCategory[0][0] += 1;
                    ratioCategory[1][0] += getTotalAmount(expenditures);
                    ratioAmount[0][0] += expenditures.getTotalAmount();
                    ratioAmount[1][0] += expenditures.getTotalAmount();
                }
                else if (expenditures.getCategory().equals("flight")) {
                    ratioCategory[0][1] += 1;
                    ratioCategory[1][1] += getTotalAmount(expenditures);
                    ratioAmount[0][1] += expenditures.getTotalAmount();
                    ratioAmount[1][1] += expenditures.getTotalAmount();
                }
                else if (expenditures.getCategory().equals("traffic")) {
                    ratioCategory[0][2] += 1;
                    ratioCategory[1][2] += getTotalAmount(expenditures);
                    ratioAmount[0][2] += expenditures.getTotalAmount();
                    ratioAmount[1][2] += expenditures.getTotalAmount();
                }
                else if (expenditures.getCategory().equals("tour")) {
                    ratioCategory[0][3] += 1;
                    ratioCategory[1][3] += getTotalAmount(expenditures);
                    ratioAmount[0][3] += expenditures.getTotalAmount();
                    ratioAmount[1][3] += expenditures.getTotalAmount();
                }
                else if (expenditures.getCategory().equals("food")) {
                    ratioCategory[0][4] += 1;
                    ratioCategory[1][4] += getTotalAmount(expenditures);
                    ratioAmount[0][4] += expenditures.getTotalAmount();
                    ratioAmount[1][4] += expenditures.getTotalAmount();
                }
                else if (expenditures.getCategory().equals("shopping")) {
                    ratioCategory[0][5] += 1;
                    ratioCategory[1][5] += getTotalAmount(expenditures);
                    ratioAmount[0][5] += expenditures.getTotalAmount();
                    ratioAmount[1][5] += expenditures.getTotalAmount();
                }
                else if (expenditures.getCategory().equals("other")) {
                    ratioCategory[0][6] += 1;
                    ratioCategory[1][6] += getTotalAmount(expenditures);
                    ratioAmount[0][6] += expenditures.getTotalAmount();
                    ratioAmount[1][6] += expenditures.getTotalAmount();
                }
            }

            response.setCategory(getPercentage(ratioCategory));
            response.setAmount(getPercentage(ratioAmount));

            return response;
        }
    }

    public static Integer getTotalAmount(Expenditures expenditures){
        int response = 0;
        for(ExpendituresGroup expendituresGroup : expenditures.getExpendituresGroups())
            response += expendituresGroup.getAmount();

        return response;
    }

    public static BudgetStatisticsDTO.Ratio getPercentage(int[][] ratioList){
        BudgetStatisticsDTO.Ratio response = new Ratio();

        int sum = 0;

        for (int i : ratioList[0]) sum += i;

        double ratio = 100.0 / sum;

        DecimalFormat df = new DecimalFormat("#.#");

        response.setLodging(Double.parseDouble(df.format(ratio * ratioList[0][0])));
        response.setFlight(Double.parseDouble(df.format(ratio * ratioList[0][1])));
        response.setTraffic(Double.parseDouble(df.format(ratio * ratioList[0][2])));
        response.setTour(Double.parseDouble(df.format(ratio * ratioList[0][3])));
        response.setFood(Double.parseDouble(df.format(ratio * ratioList[0][4])));
        response.setShopping(Double.parseDouble(df.format(ratio * ratioList[0][5])));
        response.setOther(Double.parseDouble(df.format(ratio * ratioList[0][6])));

        BudgetStatisticsDTO.amountRatio amount = new amountRatio();
        amount.setLodging(ratioList[1][0]);
        amount.setFlight(ratioList[1][1]);
        amount.setTraffic(ratioList[1][2]);
        amount.setTour(ratioList[1][3]);
        amount.setFood(ratioList[1][4]);
        amount.setShopping(ratioList[1][5]);
        amount.setOther(ratioList[1][6]);

        response.setAmount(amount);
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
        private amountRatio amount;
    }

    @Data
    public static class amountRatio{
        private double lodging;
        private double flight;
        private double traffic;
        private double tour;
        private double food;
        private double shopping;
        private double other;
    }
}
