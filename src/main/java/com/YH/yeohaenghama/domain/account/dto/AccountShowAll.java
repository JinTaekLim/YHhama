package com.YH.yeohaenghama.domain.account.dto;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.account.entity.AccountReport;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class AccountShowAll {
    @Data @Schema(name = "AccountShowAll_Response")
    public static class Response{
        private Long id;
        private String email;
        private String photoUrl;
        private String nickname;
        private Date stop;
        private List<AccountReportShow.Response> report;

        public static AccountShowAll.Response fromEntity(Account account){
            AccountShowAll.Response response = new Response();
            response.setId(account.getId());
            response.setEmail(account.getEmail());
            response.setNickname(account.getNickname());
            response.setPhotoUrl(account.getPhotoUrl());
            response.setStop(account.getStop());
            response.setReport(getReports(account));
            return response;
        }
    }

    public static List<AccountReportShow.Response> getReports(Account account){
        List<AccountReportShow.Response> reports = new ArrayList<>();
        for(AccountReport accountReport : account.getAccountReports()){
            reports.add(AccountReportShow.Response.toEntity(accountReport));
        }
        return reports;
    }
}
