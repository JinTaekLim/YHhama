package com.YH.yeohaenghama.domain.shorts.controller;

import com.YH.yeohaenghama.common.apiResult.ApiResult;
import com.YH.yeohaenghama.domain.shorts.dto.UploadShortsDTO;
import com.YH.yeohaenghama.domain.shorts.service.ShortsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/api/shorts")
@RequiredArgsConstructor
public class ShortsController {
    private final ShortsService shortsService;

    @Operation(summary = "쇼츠 등록")
    @PostMapping("/uploadShorts")
    public ApiResult<UploadShortsDTO.Response> uploadShorts(@ModelAttribute UploadShortsDTO.Request req){
        try{
            log.info("DTO ==  " + req);
            return ApiResult.success(shortsService.uploadShorts(req));
        }catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }
}
