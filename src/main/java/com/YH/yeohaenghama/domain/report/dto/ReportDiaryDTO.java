package com.YH.yeohaenghama.domain.report.dto;

import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.account.entity.Account;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class ReportDiaryDTO {

    @Schema(description = "유저ID")
    private Long accountId;
    @Schema(description = "일기ID")
    private Long diaryId;

    @Data
    public static class Response {
        @Schema(description = "일기 ID")
        private Long diaryId;
        @Schema(description = "일기 제목")
        private String diaryTitle;
        @Schema(description = "일기 작성자")
        private AccountShowDTO.Response writer;
        @Schema(description = "신고 누적 횟수")
        private int reportCount;
        @Schema(description = "작성 날짜")
        private LocalDateTime date;


        public Response(Long diaryId, String diaryTitle, Account account, int reportCount, LocalDateTime date) {
            this.diaryId = diaryId;
            this.diaryTitle = diaryTitle;

            AccountShowDTO.Response accountDTO = new AccountShowDTO.Response(account.getId(),account.getNickname(), account.getPhotoUrl(), account.getRole());
            this.writer = accountDTO;
            this.reportCount = reportCount;
            this.date = date;
        }
    }

}
