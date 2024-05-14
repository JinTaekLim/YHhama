package com.YH.yeohaenghama.domain.account.dto;

import com.YH.yeohaenghama.domain.account.entity.AccountReport;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

public class AccountReportShow {
    @Data @Schema(name = "AccountReportShow_Response")
    public static class Response{
        Date warningDate;

        public static AccountReportShow.Response toEntity(AccountReport accountReport){
            AccountReportShow.Response response = new AccountReportShow.Response();
            response.setWarningDate(accountReport.getWarningDate());
            return response;
        }
    }
}
