package com.YH.yeohaenghama.domain.search.controller;


import com.YH.yeohaenghama.common.apiResult.ApiResult;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.openApi.dto.OpenApiAreaDTO;
import com.YH.yeohaenghama.domain.openApi.dto.SearchAreaDTO;
import com.YH.yeohaenghama.domain.openApi.service.OpenApiService;
import com.YH.yeohaenghama.domain.search.dto.SearchDTO;
import com.YH.yeohaenghama.domain.search.dto.SearchDiaryDTO;
import com.YH.yeohaenghama.domain.search.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final OpenApiService openApiService;



    @Operation(summary = "통합 검색")
    @PostMapping("/federated")
    public ApiResult<SearchDTO.Response> federated(@RequestBody SearchDTO.Request dto) {
        try {
            return ApiResult.success(searchService.federated(dto));
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "관광지/음식점 키워드 검색")
    @PostMapping("/area")
    public SearchAreaDTO.Response test(@RequestBody SearchAreaDTO.Reqeust req) throws Exception {
        return openApiService.searchArea(req);
    }

    @Operation(summary = "일기 제목 검색")
    @PostMapping("/diaryTitle")
    public ApiResult<SearchDiaryDTO.Response> diaryTitle(@RequestBody SearchDTO.Request dto) {
        try {
            return ApiResult.success(searchService.searchTitle(dto));
        }catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "일기 내용 검색")
    @PostMapping("/diaryContent")
    public ApiResult<SearchDiaryDTO.Response> diaryContent(@RequestBody SearchDTO.Request dto) {
        try {
            return ApiResult.success(searchService.searchContent(dto));
        }catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "특정 장소가 포함된 일기 검색")
    @PostMapping("/diaryPlace")
    public ApiResult<SearchDiaryDTO.Response> diaryPlace(@RequestBody SearchDTO.Request dto) {
        try {
            return ApiResult.success(searchService.serachPlace(dto));
        }catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "특정 지역에 생성된 일기 검색")
    @PostMapping("/diaryArea")
    public ApiResult<SearchDiaryDTO.Response> diaryArea(@RequestBody SearchDTO.Request dto) {
        try {
            return ApiResult.success(searchService.searchArea(dto));
        }catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "테스트")
    @PostMapping("/test")
    public ApiResult<SearchDiaryDTO.Response> test() {
        try {
            return ApiResult.success(searchService.findAll());
        }catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }
}
