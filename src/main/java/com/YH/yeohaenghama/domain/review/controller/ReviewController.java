package com.YH.yeohaenghama.domain.review.controller;

import com.YH.yeohaenghama.common.apiResult.ApiResult;
import com.YH.yeohaenghama.domain.review.dto.*;
import com.YH.yeohaenghama.domain.review.service.ReviewService;
import com.google.protobuf.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;
    @Operation(summary = "리뷰 등록")
    @PostMapping("/join")
    public ApiResult<ReviewDTO.Response> join(@ModelAttribute ReviewDTO.Request dto){
        try{
            log.info("DTO ===== : " + dto);
            ReviewDTO.Response response = reviewService.join(dto);
            return ApiResult.success(response);
        }catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }
        catch (DataIntegrityViolationException e){
            return ApiResult.success(null,e.getMessage());
        }
        catch (Exception e){
            log.info(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }


    @Operation(summary = "리뷰 확인")
    @PostMapping("/show")
    public ApiResult reviewShow(@RequestBody ReviewDTO.Show dto){
        try{
            return ApiResult.success(reviewService.reviewShow(dto));
        }catch (NoSuchElementException e){
            return ApiResult.success(e.getMessage());
        }
        catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }


    @Operation(summary = "특정 장소의 모든 리뷰 확인")
    @PostMapping("/showAll")
    public ApiResult<List<ReviewShowAllDTO.Response>> reviewShowAll(@RequestBody ReviewShowAllDTO.Request dto){
        try{
            return ApiResult.success(reviewService.reviewShowAll(dto));
        }catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }
        catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }


    @Operation(summary = "특정 유저의 모든 리뷰 확인")
    @PostMapping("/showAccountAll")
    public ApiResult<List<ReviewAccountShowDTO.Response>> reviewAccountShowAll(@RequestBody ReviewAccountShowDTO.Request dto){
        try{
            return ApiResult.success(reviewService.reviewAccountShow(dto));
        }catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }
        catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }




    @Operation(summary = "평점 확인")
    @PostMapping("/ratingShow")
    public ApiResult ratingShow(@RequestBody ReviewShowDTO.Request dto){
        try{
            return ApiResult.success(reviewService.ratingShow(dto));
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }


    @Operation(summary = "리뷰 삭제")
    @PostMapping("/delete")
    public ApiResult<String> delete(@RequestBody ReviewDeleteDTO.Request dto){
        try{
            return ApiResult.success(reviewService.delete(dto));
        }catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }
        catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "리뷰 수정")
    @PostMapping("/update")
    public ApiResult<ReviewDTO.Response> update(@RequestParam Long reviewId,@ModelAttribute ReviewDTO.Request dto){
        try{
            log.info("DTO = " + dto);
            return ApiResult.success(reviewService.update(reviewId,dto));
        }catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "리뷰 유무 확인")
    @PostMapping("/check")
    public ApiResult check(@RequestBody ReviewDTO.Show dto){
        try {
            return ApiResult.success(reviewService.check(dto));
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }
}
