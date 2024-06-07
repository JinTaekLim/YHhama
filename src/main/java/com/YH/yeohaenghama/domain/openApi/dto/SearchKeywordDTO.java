package com.YH.yeohaenghama.domain.openApi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SearchKeywordDTO {
    @Schema(name = "SearchKeywordDTO_Request") @Data
    public static class Request{
        private String keyword;
    }

    @Schema(name = "SearchKeywordDTO_Response") @Data
    public static class Response{
    }
}
