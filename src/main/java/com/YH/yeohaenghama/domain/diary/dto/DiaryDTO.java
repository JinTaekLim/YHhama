package com.YH.yeohaenghama.domain.diary.dto;

import com.YH.yeohaenghama.domain.diary.entity.Diary;
import com.YH.yeohaenghama.domain.diary.entity.DiaryPhotoUrl;
import com.YH.yeohaenghama.domain.itinerary.dto.ItineraryShowDTO;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.JoinColumn;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class DiaryDTO {
    @Data
    public static class Request{
        @Schema(description = "일정 ID")
        private Long itinerary;
        @Schema(description = "일기 작성 일시")
        private String date;
        @Schema(description = "일기 제목")
        private String title;
        @Schema(description = "일기 내용")
        private String content;
        @Schema(description = "일기 사진 URL")
        private List<MultipartFile> photos;
        @Schema(description = "날짜 별 일기")
        private List<DiaryDetailDTO.Request> detail;
    }

    @Data
    public static class Response {
        @Schema(description = "일정 ID")
        private Long itinerary;
        @Schema(description = "일기 작성 일시")
        private String date;
        @Schema(description = "일기 제목")
        private String title;
        @Schema(description = "일기 내용")
        private String content;
//        @Schema(description = "날짜 별 일기")
//        private List<DiaryDetailDTO.Request> detail;

        public static Response fromEntity(Diary diary) {
            Response response = new Response();
            response.setItinerary(diary.getItinerary());
            response.setDate(diary.getDate());
            response.setTitle(diary.getTitle());
            response.setContent(diary.getContent());

            return response;
        }

    }

    private Request request;
    private Response response;
    public DiaryDTO(Request request) { this.request = request; }
    public DiaryDTO(Response response) { this.response = response; }


    public Diary toEntity() {
        return Diary.builder()
                .itinerary(request.getItinerary())
                .date(request.getDate())
                .title(request.getTitle())
                .content(request.getContent())
                .itinerary(request.getItinerary())
                .build();
    }

}
