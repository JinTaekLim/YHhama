package com.YH.yeohaenghama.domain.rating.dto;

import lombok.Data;

@Data
public class RatingDeleteDTO {
    @Data
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
