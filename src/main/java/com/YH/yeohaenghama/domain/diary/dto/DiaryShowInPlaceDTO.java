package com.YH.yeohaenghama.domain.diary.dto;

import com.YH.yeohaenghama.domain.diary.entity.Diary;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class DiaryShowInPlaceDTO {

    @Data
    @Schema(name = "DiaryShowInPlaceDTO_Request")
    public static class Request{
        @Schema(description = "장소 번호")
        private String placeNum;
        @Schema(description = "타입 번호")
        private String typeNum;
    }

    @Data
    public static class Response {
        @Schema(description = "일정 ID")
        private Long itinerary;
        @Schema(description = "일기 작성 일시")
        private LocalDateTime date;
        @Schema(description = "일기 제목")
        private String title;
        @Schema(description = "일기 내용")
        private String content;
        @Schema(description = "일기 사진 URL")
        private String photoUrl;


        public static DiaryShowInPlaceDTO.Response fromEntity(Diary diary,String photoUrl) {
            DiaryShowInPlaceDTO.Response response = new DiaryShowInPlaceDTO.Response();
            response.setItinerary(diary.getItinerary());
            response.setDate(diary.getDate());
            response.setTitle(diary.getTitle());
            response.setContent(diary.getContent());

            response.setPhotoUrl(photoUrl);

            return response;
        }
    }
}

