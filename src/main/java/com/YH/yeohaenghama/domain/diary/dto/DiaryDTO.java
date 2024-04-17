package com.YH.yeohaenghama.domain.diary.dto;

import com.YH.yeohaenghama.domain.diary.entity.Diary;
import com.YH.yeohaenghama.domain.diary.entity.DiaryPhotoUrl;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Schema(name = "DiaryDTO")
public class DiaryDTO {

    @Schema(description = "일기 사진 URL")
    private List<DiaryPhotoUrl> photos;

    @Data
    @Schema(name = "DiaryDTO_Request")
    public static class Request{
        @Schema(description = "일정 ID")
        private Long itinerary;
        @Schema(description = "일기 제목")
        private String title;
        @Schema(description = "일기 내용")
        private String content;
        @Schema(description = "일기 사진 URL")
        private List<MultipartFile> photos;
    }

    @Data
    @Schema(name = "DiaryDTO_Response")
    public static class Response {
        @Schema(description = "일정 ID")
        private Long itinerary;
        @Schema(description = "일기 작성 일시")
        private LocalTime date;
        @Schema(description = "일기 제목")
        private String title;
        @Schema(description = "일기 내용")
        private String content;
        @Schema(description = "일기 사진 URL")
        private List<String> photos;

        public static Response fromEntity(Diary diary) {
            Response response = new Response();
            response.setItinerary(diary.getItinerary());
            response.setDate(diary.getDate());
            response.setTitle(diary.getTitle());
            response.setContent(diary.getContent());

            List<String> photoURLs = new ArrayList<>();
            List<DiaryPhotoUrl> diaryPhotoUrls = diary.getDiaryPhotoUrls();
            for (DiaryPhotoUrl photoUrl : diaryPhotoUrls) {
                photoURLs.add(photoUrl.getPhotoURL());
            }
            response.setPhotos(photoURLs);


            return response;
        }

    }

    private Request request;
    private Response response;
}
