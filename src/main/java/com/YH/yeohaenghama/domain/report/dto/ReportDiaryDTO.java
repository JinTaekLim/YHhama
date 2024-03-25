package com.YH.yeohaenghama.domain.report.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ReportDiaryDTO {

    @Schema(description = "1 : 일기 , 2 : 리뷰")
    private Long typeId;
    @Schema(description = "유저ID")
    private Long accountId;
    @Schema(description = "일기ID")
    private Long diaryId;



}
