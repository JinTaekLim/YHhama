package com.YH.yeohaenghama.domain.test.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;


@Data
public class testDTO {
    @Data
    public static class Request {
        @Schema(description = "일정 ID")
        private Long itinerary;
        @Schema(description = "일기 작성 일시")
        private String date;
        @Schema(description = "일기 제목")
        private String title;
        @Schema(description = "일기 내용")
        private String content;
        //        @Schema(description = "일기 사진")
//        private List<MultipartFile> photo;
        @Schema(description = "일기 디테일")
        private List<test2DTO.Request> test2DTO;

    }

    ;

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
        @Schema(description = "일기 사진")
        private List<String> photo;
        @Schema(description = "일기 디테일")
        private List<test2DTO.Response> test2DTO;

        public void setTest2DTO(List<test2DTO.Response> test2DTOList) { // 변경된 메서드
            this.test2DTO = test2DTOList;

        };
    }
}