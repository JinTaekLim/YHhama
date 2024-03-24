package com.YH.yeohaenghama.domain.report.dto;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.diary.entity.Comment;
import com.YH.yeohaenghama.domain.report.entity.ReportDiary;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportDiaryDTO {

    @Schema(description = "유저ID")
    private Long accountId;
    @Schema(description = "일기ID")
    private Long diaryId;



}
