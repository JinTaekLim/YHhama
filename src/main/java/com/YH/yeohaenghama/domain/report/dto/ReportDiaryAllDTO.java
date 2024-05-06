package com.YH.yeohaenghama.domain.report.dto;

import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.diary.dto.DiaryShowDTO;
import com.YH.yeohaenghama.domain.diary.entity.Diary;
import com.YH.yeohaenghama.domain.diary.entity.DiaryPhotoUrl;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReportDiaryAllDTO {
    @Data @Schema(description = "ReportDiaryAllDTO_Response")
    public static class Response {
        @Schema(description = "일기 ID")
        private Long diaryId;
        @Schema(description = "일기 제목")
        private String diaryTitle;
        @Schema(description = "일기 작성자")
        private String writer;
        @Schema(description = "신고 누적 횟수")
        private Integer reportCount;
        @Schema(description = "작성 날짜")
        private LocalDateTime date;


        public static ReportDiaryAllDTO.Response fromEntity(Diary diary, Account account, Integer reportCount) {
            ReportDiaryAllDTO.Response response = new ReportDiaryAllDTO.Response();
            response.setDiaryId(diary.getId());
            response.setDiaryTitle(diary.getTitle());
            response.setWriter(account.getNickname());
            response.setReportCount(reportCount);
            response.setDate(diary.getDate());



            return response;
        }
    }
}
