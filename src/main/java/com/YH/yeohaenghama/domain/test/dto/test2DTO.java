package com.YH.yeohaenghama.domain.test.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class test2DTO {
    @Data
    public static class Request{
        @Schema(description = "일기 날짜")
        private String day;
        @Schema(description = "일기 내용")
        private String content;
//        @Schema(description = "일기 사진")
//        private List<MultipartFile> photo;
    }
    @Data
    public static class Response{
        @Schema(description = "일기 날짜")
        private String day;
        @Schema(description = "일기 내용")
        private String content;
        @Schema(description = "일기 사진 URL")
        private List<String> photoUrl;
    }
}
