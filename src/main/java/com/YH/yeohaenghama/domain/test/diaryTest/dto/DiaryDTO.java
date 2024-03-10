//package com.YH.yeohaenghama.domain.test.diary.dto;
//
//import com.YH.yeohaenghama.domain.test.diary.entity.Diary;
//import io.swagger.v3.oas.annotations.media.Schema;
//import lombok.Data;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//
//@Data
//public class DiaryDTO {
//    @Data
//    public static class Request{
//        @Schema(description = "일정 ID")
//        private Long itinerary;
//        @Schema(description = "일기 작성 일시")
//        private String date;
//        @Schema(description = "일기 제목")
//        private String title;
//        @Schema(description = "일기 내용")
//        private String content;
//        @Schema(description = "일기 사진 URL")
//        private List<MultipartFile> photos;
//        @Schema(description = "날짜 별 일기")
//        private List<DiaryDetailDTO.Request> detail;
//    }
//
//    @Data
//    public static class Response {
//        @Schema(description = "일정 ID")
//        private Long itinerary;
//        @Schema(description = "일기 작성 일시")
//        private String date;
//        @Schema(description = "일기 제목")
//        private String title;
//        @Schema(description = "일기 내용")
//        private String content;
////        @Schema(description = "날짜 별 일기")
////        private List<DiaryDetailDTO.Request> detail;
//
//        public static Response fromEntity(Diary diary) {
//            Response response = new Response();
//            response.setItinerary(diary.getItinerary());
//            response.setDate(diary.getDate());
//            response.setTitle(diary.getTitle());
//            response.setContent(diary.getContent());
//
//            return response;
//        }
//
//    }
//
//    private Request request;
//    private Response response;
//    public DiaryDTO(Request request) { this.request = request; }
//    public DiaryDTO(Response response) { this.response = response; }
//
//
//    public Diary toEntity() {
//        return Diary.builder()
//                .itinerary(request.getItinerary())
//                .date(request.getDate())
//                .title(request.getTitle())
//                .content(request.getContent())
//                .itinerary(request.getItinerary())
//                .build();
//    }
//
//}
