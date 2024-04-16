package com.YH.yeohaenghama.domain.search.controller;


import com.YH.yeohaenghama.common.apiResult.ApiResult;
import com.YH.yeohaenghama.domain.openApi.dto.OpenApiAreaDTO;
import com.YH.yeohaenghama.domain.search.dto.SearchDTO;
import com.YH.yeohaenghama.domain.search.dto.SearchDiaryDTO;
import com.YH.yeohaenghama.domain.search.service.SearchService;
import com.google.protobuf.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/search")
public class SearchController {
    private final SearchService searchService;



    @Operation(summary = "통합 검색")
    @PostMapping("/federated")
    public ApiResult<SearchDTO.Response> federated(SearchDTO.Request dto) {
        try {
            return ApiResult.success(searchService.federated(dto));
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "일기 제목 검색")
    @PostMapping("/diaryTitle")
    public ApiResult<List<SearchDiaryDTO>> diaryTitle(SearchDTO.Request dto) {
        try {
            return ApiResult.success(searchService.serachTitle(dto));
        }catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "일기 내용 검색")
    @PostMapping("/diaryContent")
    public ApiResult<List<SearchDiaryDTO>> diaryContent(SearchDTO.Request dto) {
        try {
            return ApiResult.success(searchService.serachContent(dto));
        }catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "특정 장소가 포함된 일기 검색")
    @PostMapping("/diaryPlace")
    public ApiResult<List<SearchDiaryDTO>> diaryPlace(SearchDTO.Request dto) {
        try {
            return ApiResult.success(searchService.serachPlace(dto));
        }catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }
}
