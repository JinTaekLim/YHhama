package com.YH.yeohaenghama.domain.budget.dto;

import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.account.entity.Account;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class calculateIndividualDTO {
    @Data @Schema(name = "calculateExpendituresDTO_Response")
    public static class Response{
        private AccountShowDTO.Response account;
        private Integer amount;

        public static calculateIndividualDTO.Response fromEntity(Account account, Integer amount){
            calculateIndividualDTO.Response response = new calculateIndividualDTO.Response();
            response.setAccount(getAccount(account));
            response.setAmount(amount);
            return response;
        }
        private static AccountShowDTO.Response getAccount(Account account){
            return new AccountShowDTO.Response(account.getId(), account.getNickname(), account.getPhotoUrl(), account.getRole());
        }


    }

}
