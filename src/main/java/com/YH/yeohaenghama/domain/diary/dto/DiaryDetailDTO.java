package com.YH.yeohaenghama.domain.diary.dto;

import com.YH.yeohaenghama.domain.diary.entity.DiaryDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

public class DiaryDetailDTO {
    @Data
    public static class Request{
        @Schema(description = "다이어리 ID")
        private Long diaryId;
        @Schema(description = "일기 날짜")
        private String day;
        @Schema(description = "일기 내용")
        private String content;
        @Schema(description = "일기 사진 URL")
        private List<String> photoURL;
    }

    @Data
    public static class Response {
        @Schema(description = "일기 날짜")
        private String day;
        @Schema(description = "일기 내용")
        private String content;
        @Schema(description = "일기 사진 URL")
        private List<String> photoURL;

        public static Response fromEntity(DiaryDetail diaryDetail) {
            Response response = new Response();
            response.setDay(diaryDetail.getDay());
            response.setContent(diaryDetail.getContent());

            return response;
        }

    }

    private Request request;
    private Response response;

    public DiaryDetailDTO(Request request) { this.request = request; }
    public DiaryDetailDTO(Response response) { this.response = response; }


    public DiaryDetail toEntity() {
        return DiaryDetail.builder()
                .diary(request.getDiaryId())
                .day(request.getDay())
                .content(request.getContent())
                .build();
    }
}
