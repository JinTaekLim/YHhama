package com.YH.yeohaenghama.domain.rating.controller;

import com.YH.yeohaenghama.common.apiResult.ApiResult;
import com.YH.yeohaenghama.domain.rating.dto.RatingDTO;
import com.YH.yeohaenghama.domain.rating.dto.RatingShowDTO;
import com.YH.yeohaenghama.domain.rating.service.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/rating")
public class RatingController {

    private final RatingService ratingService;
    @Operation(summary = "평점 등록")
    @PostMapping("/join")
    public ApiResult<RatingDTO.Response> join(RatingDTO.Request dto){
        try{
            RatingDTO.Response response = ratingService.join(dto);
            return ApiResult.success(response);
        }catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }
        catch (Exception e){
            log.info(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "평점 확인")
    @PostMapping("/show")
    public ApiResult show(@RequestBody RatingShowDTO.Request dto){
        try{
            return ApiResult.success(ratingService.show(dto));
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }
}
