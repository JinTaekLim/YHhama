package com.YH.yeohaenghama.domain.search.dto;

import com.YH.yeohaenghama.domain.openApi.dto.OpenApiAreaDTO;
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
        private List<SearchDiaryDTO> searchDiaryTitle;
        private List<SearchDiaryDTO> searchDiaryContent;
        private List<SearchDiaryDTO> searchDiaryPlace;

        private List<OpenApiAreaDTO.Response.Body.Items.Item> searchPlace;

        public static Response setSearch(List<SearchDiaryDTO> searchDiaryTitle, List<SearchDiaryDTO> searchDiaryContent, List<SearchDiaryDTO> searchDiaryPlace, List<OpenApiAreaDTO.Response.Body.Items.Item> searchPlace){
            Response response = new Response();
            response.searchDiaryTitle = searchDiaryTitle;
            response.searchDiaryContent = searchDiaryContent;
            response.searchDiaryPlace = searchDiaryPlace;
            response.searchPlace = searchPlace;

            return response;
        }
    }
}
