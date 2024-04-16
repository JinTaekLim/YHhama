package com.YH.yeohaenghama.domain.search.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

public class SearchDTO {
    @Data
    @Schema(description = "SearchDTO_Request")
    public static class Request{
        private String keyWord;
    }

    @Data
    @Schema(description = "SearchDTO_Response")
    public static class Response{
        private List<SearchDiaryDTO> searchDiaryDTO;
    }
}
