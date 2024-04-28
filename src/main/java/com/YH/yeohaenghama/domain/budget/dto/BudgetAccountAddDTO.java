package com.YH.yeohaenghama.domain.budget.dto;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.budget.entity.Budget;
import com.YH.yeohaenghama.domain.budget.entity.BudgetAccount;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class BudgetAccountAddDTO {
    @Data @Schema(name = "BudgetAccountAddDTO_Request")
    public static class Request{
        private Long accountId;
        private Long budgetId;
    }

    public BudgetAccount add(Account account, Budget budget){
        BudgetAccount budgetAccount = BudgetAccount.builder()
                .account(account)
                .budget(budget)
                .build();
        return budgetAccount;
    }
}
