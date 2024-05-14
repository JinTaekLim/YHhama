package com.YH.yeohaenghama.domain.account.dto;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.account.entity.AccountReport;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class AccountReportDTO {
    @Data @Schema(name = "AccountReportDTO.Request")
    public static class Request{
        private Long id;
        private Long accountId;
    }

    @Data @Schema(name = "AccountReportDTO.Response")
    public static class Response{
        private Long id;
        private String email;
        private String photoUrl;
        private String nickname;
        private Date stop=null;
        private Long warning;

        public AccountReportDTO.Response fromEntity(Account account){
            AccountReportDTO.Response response = new Response();
            response.setId(account.getId());
            response.setEmail(account.getEmail());
            response.setPhotoUrl(account.getPhotoUrl());
            response.setNickname(account.getNickname());
            response.setStop(account.getStop());
            response.setWarning((long) account.getAccountReports().size());
            return response;
        }
    }

    public static AccountReport toEntity(Account account){
        AccountReport accountReport = AccountReport.builder()
                .account(account)
                .warningDate(new Date())
                .build();
        return accountReport;
    }
}
