package com.YH.yeohaenghama.domain.review.dto;

import lombok.Data;

@Data
public class ReviewShowDTO {
    @Data
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
