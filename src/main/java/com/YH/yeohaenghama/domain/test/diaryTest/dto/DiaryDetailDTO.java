//package com.YH.yeohaenghama.domain.test.diary.dto;
//
//import com.YH.yeohaenghama.domain.test.diary.entity.Diary;
//import com.YH.yeohaenghama.domain.test.diary.entity.DiaryDetail;
//import com.YH.yeohaenghama.domain.test.diary.entity.DiaryDetailPhotoURL;
//import io.swagger.v3.oas.annotations.media.Schema;
//import lombok.Data;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class DiaryDetailDTO {
//    @Data
//    public static class Request{
//        @Schema(description = "일기 날짜")
//        private String day;
//        @Schema(description = "일기 내용")
//        private String content;
//        @Schema(description = "일기 사진 URL")
//        private List<MultipartFile> photos;
//        @Schema(description = "일기 ID")
//        private Diary diary;
//    }
//
//    @Data
//    public static class Response {
//        @Schema(description = "일기 날짜")
//        private String day;
//        @Schema(description = "일기 내용")
//        private String content;
//        @Schema(description = "일기 사진 URL")
//        private List<String> photoURL;
//
//        public static Response fromEntity(DiaryDetail diaryDetail) {
//            Response response = new Response();
//            response.setDay(diaryDetail.getDay());
//            response.setContent(diaryDetail.getContent());
//
//            List<String> photoURLs = new ArrayList<>();
//            for (DiaryDetailPhotoURL photoURL : diaryDetail.getDiaryDetailPhotoURLS()) {
//                photoURLs.add(photoURL.getPhotoURL());
//            }
//            response.setPhotoURL(photoURLs);
//
//
//            return response;
//        }
//
//    }
//
//    private Request request;
//    private Response response;
//
//    public DiaryDetailDTO(Request request) { this.request = request; }
//    public DiaryDetailDTO() { this.response = response; }
//
//
//    public DiaryDetail toEntity() {
//        return DiaryDetail.builder()
//                .day(request.getDay())
//                .content(request.getContent())
//                .diary(request.getDiary())
//                .build();
//    }
//}
