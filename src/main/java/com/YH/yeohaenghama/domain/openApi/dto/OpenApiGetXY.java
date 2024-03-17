package com.YH.yeohaenghama.domain.openApi.dto;

import lombok.Data;
import lombok.Setter;

@Setter
public class OpenApiGetXY {
    @Data
    public static class Request{
        private String contentId;
        private String contentType;
    }

    @Data
    public static class Response {
        private String x;
        private String y;
    }
}
