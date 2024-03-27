package com.YH.yeohaenghama.domain.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ReviewDeleteDTO {
    @Data
    @Schema(name = "ReviewDeleteDTO_Request")
    public static class Request{
        private Long contentTypeId;
        private Long contentId;
        private Long accountId;
    }
    @Data
    public static class Response{
        private Long contentTypeId;
        private Long contentId;
        private Long accountId;
    }
}
