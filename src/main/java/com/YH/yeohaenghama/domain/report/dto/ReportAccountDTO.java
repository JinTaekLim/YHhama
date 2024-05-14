package com.YH.yeohaenghama.domain.report.dto;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.report.entity.ReportAccount;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ReportAccountDTO {
    @Data @Schema(name = "ReportAccountDTO_Request")
    public static class Request{
        private Long accountId;
        private Long reportAccountId;
    }

    public static ReportAccount fromEntity(Account account, Account reportAccount){
        ReportAccount response = ReportAccount.builder()
                .account(account)
                .reportAccount(reportAccount)
                .build();
        return response;
    }
}
