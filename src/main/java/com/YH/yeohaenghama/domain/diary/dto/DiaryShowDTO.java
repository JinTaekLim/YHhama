package com.YH.yeohaenghama.domain.diary.dto;

import com.YH.yeohaenghama.domain.diary.entity.DiaryDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class DiaryShowDTO {
    @Schema(description = "일정 ID")
    private Long itinerary;
    @Schema(description = "일기 작성 일시")
    private String date;
    @Schema(description = "일기 제목")
    private String title;
    @Schema(description = "일기 내용")
    private String content;
    @Schema(description = "일기 사진 URL")
    private List<String> photos;


    private List<DiaryDetailDTO.Response> diaryDetailDTO;
}
