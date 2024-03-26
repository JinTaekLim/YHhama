package com.YH.yeohaenghama.domain.report.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReportShowDiaryDTO {
    @Schema(description = "일기 ID")
    private Long diaryId;
    @Schema(description = "일기 제목")
    private String diaryTitle;
    @Schema(description = "일기 작성자")
    private String writer;
    @Schema(description = "신고 누적 횟수")
    private int reportCount;
    @Schema(description = "작성 날짜")
    private String date;
}
