package com.YH.yeohaenghama.domain.search.controller;


import com.YH.yeohaenghama.domain.openApi.dto.OpenApiAreaDTO;
import com.YH.yeohaenghama.domain.search.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/search")
public class SearchController {
    private final SearchService searchService;



    @Operation(summary = "통합 검색")
    @PostMapping("/federated")
    public void federated() {

    }
}
