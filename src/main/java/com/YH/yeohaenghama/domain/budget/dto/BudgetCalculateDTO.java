package com.YH.yeohaenghama.domain.budget.dto;

import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.budget.entity.Expenditures;
import com.YH.yeohaenghama.domain.budget.entity.ExpendituresGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class BudgetCalculateDTO {
    @Schema(name = "BudgetCalculateDTO_Request") @Data
    public static class Request{
        private Long budgetId;
        private Long accountId;
    }

    @Schema(name = "BudgetCalculateDTO_Response") @Data
    public static class Response{
        private Long budgetId;
        private AccountShowDTO.Response account;

        private List<calculateIndividualDTO.Response> individual;

        private List<calculateGroupDTO.Response> group;
        public Response fromEntity(Long budgetId, Account account, List<Expenditures> expenditures, List<ExpendituresGroup> expendituresGroups){
            BudgetCalculateDTO.Response response = new Response();
            response.setBudgetId(budgetId);
            response.setAccount(accountDTO(account));
            response.setIndividual(individualDTO(expenditures));
            response.setGroup(GroupDTO(expendituresGroups));

            return response;
        }
    }

    public static AccountShowDTO.Response accountDTO(Account account){
        return new AccountShowDTO.Response(account.getId(), account.getNickname(), account.getPhotoUrl(), account.getRole());
    }

    public static List<calculateIndividualDTO.Response> individualDTO(List<Expenditures> expenditures){
        List<calculateIndividualDTO.Response> responses = new ArrayList<>();
        for(Expenditures expenditure : expenditures){
            responses.add(calculateIndividualDTO.Response.fromEntity(expenditure));
        }

        return responses;
    }

    public static List<calculateGroupDTO.Response> GroupDTO(List<ExpendituresGroup> expendituresGroups){
        List<calculateGroupDTO.Response> responses = new ArrayList<>();
        for(ExpendituresGroup expendituresGroup : expendituresGroups){
            responses.add(calculateGroupDTO.Response.fromEntity(expendituresGroup));
        }

        return responses;
    }
}
