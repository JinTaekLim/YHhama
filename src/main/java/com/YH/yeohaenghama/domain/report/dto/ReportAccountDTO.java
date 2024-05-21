package com.YH.yeohaenghama.domain.report.dto;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.report.entity.ReportAccount;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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

    public static List<ReportAccount> deleteAccount(List<ReportAccount> reportAccountList){
        List<ReportAccount> response = new ArrayList<>();

        for(ReportAccount reportAccount : reportAccountList){
                reportAccount.deleteAccount();
                response.add(reportAccount);
        }


        return response;
    }
}
