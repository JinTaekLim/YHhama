package com.YH.yeohaenghama.domain.search.dto;

import com.YH.yeohaenghama.domain.openApi.dto.OpenApiAreaDTO;
import com.YH.yeohaenghama.domain.openApi.dto.SearchAreaDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

public class SearchDTO {
    @Data
    @Schema(description = "SearchDTO_Request")
    public static class Request{
        private String keyWord;
        private Integer numOfRows;
        private Integer page;
    }

    @Data
    @Schema(description = "SearchDTO_Response")
    public static class Response{
        private SearchDiaryDTO.Response searchDiaryTitle;
        private SearchDiaryDTO.Response searchDiaryContent;
        private SearchDiaryDTO.Response searchDiaryPlace;
        private SearchDiaryDTO.Response searchDiaryArea;

        private SearchAreaDTO.Response searchPlace;

        public static Response setSearch(SearchDiaryDTO.Response searchDiaryTitle,
                                         SearchDiaryDTO.Response searchDiaryContent,
                                         SearchDiaryDTO.Response searchDiaryPlace,
                                         SearchDiaryDTO.Response searchDiaryArea ,
                                         SearchAreaDTO.Response searchPlace){
            Response response = new Response();
            response.searchDiaryTitle = searchDiaryTitle;
            response.searchDiaryContent = searchDiaryContent;
            response.searchDiaryPlace = searchDiaryPlace;
            response.searchDiaryArea = searchDiaryArea;
            response.searchPlace = searchPlace;

            return response;
        }
    }
}
