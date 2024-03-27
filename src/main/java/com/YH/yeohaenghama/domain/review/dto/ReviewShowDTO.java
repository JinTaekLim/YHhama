package com.YH.yeohaenghama.domain.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ReviewShowDTO {
    @Data
    @Schema(name = "ReviewShowDTO_Request")
    public static class Request{
        private Long contentTypeId;
        private Long contentId;
    }
    @Data
    public static class Response{
        private Long ratingNum;
        private Long totalRating;
    }
}
