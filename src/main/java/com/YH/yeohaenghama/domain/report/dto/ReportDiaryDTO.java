package com.YH.yeohaenghama.domain.report.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalTime;

@Data
public class ReportDiaryDTO {

    @Schema(description = "유저ID")
    private Long accountId;
    @Schema(description = "일기ID")
    private Long diaryId;

    @Data
    public static class Request {
        @Schema(description = "일기 ID")
        private Long diaryId;
        @Schema(description = "일기 제목")
        private String diaryTitle;
        @Schema(description = "일기 작성자")
        private String writer;
        @Schema(description = "신고 누적 횟수")
        private int reportCount;
        @Schema(description = "작성 날짜")
        private LocalTime date;


        public Request(Long diaryId, String diaryTitle, String writer, int reportCount, LocalTime date) {
            this.diaryId = diaryId;
            this.diaryTitle = diaryTitle;
            this.writer = writer;
            this.reportCount = reportCount;
            this.date = date;
        }
    }

}
