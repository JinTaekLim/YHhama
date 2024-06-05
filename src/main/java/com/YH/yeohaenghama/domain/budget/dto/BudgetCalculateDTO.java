package com.YH.yeohaenghama.domain.budget.dto;

import com.YH.yeohaenghama.domain.account.entity.Account;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class BudgetCalculateDTO {
    @Schema(name = "BudgetCalculateDTO_Request") @Data
    public static class Request{
        private Long expendituresId;
        private Long payerId;
        private List<Long> accountId;
        private List<Integer> amount;
    }

    @Schema(name = "BudgetCalculateDTO_Response") @Data
    public static class Response{
        private calculateIndividualDTO.Response payer;
        private List<calculateIndividualDTO.Response> account;

        public static Response toEntity(Account payer, Integer totalAmount, List<Account> accountList, List<Integer> amountList){
            Response response = new Response();

            List<calculateIndividualDTO.Response> accountCalculate = new ArrayList<>();
            for(int i =0; i<accountList.size(); i++) {
                accountCalculate.add(calculateIndividualDTO.Response.fromEntity(accountList.get(i),amountList.get(i)));
            }


            response.setAccount(accountCalculate);

            for(Integer i : amountList){ totalAmount -= i; }

            response.setPayer(calculateIndividualDTO.Response.fromEntity(payer,totalAmount));
            return response;
        }
    }
}
