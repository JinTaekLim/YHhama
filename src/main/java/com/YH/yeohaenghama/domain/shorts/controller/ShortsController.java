package com.YH.yeohaenghama.domain.shorts.controller;

import com.YH.yeohaenghama.common.apiResult.ApiResult;
import com.YH.yeohaenghama.domain.shorts.dto.*;
import com.YH.yeohaenghama.domain.shorts.service.ShortsCommentService;
import com.YH.yeohaenghama.domain.shorts.service.ShortsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/api/shorts")
@RequiredArgsConstructor
public class ShortsController {
    private final ShortsService shortsService;
    private final ShortsCommentService shortsCommentService;

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

    @Operation(summary = "쇼츠 수정")
    @PostMapping("/updateShorts")
    public ApiResult<UploadShortsDTO.Response> uploadShorts(@ModelAttribute UpdateShortsDTO.Request req, @RequestParam("shortsId") Long shortsId){
        try{
            log.info("DTO ==  " + req);
            return ApiResult.success(shortsService.updateShorts(req,shortsId));
        }catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "쇼츠 조회")
    @PostMapping("/readShorts")
    public ApiResult<ReadShortsDTO.AllResponse> readShorts(ReadShortsDTO.AllRequest req){
        try{
            log.info("DTO ==  " + req);
            return ApiResult.success(shortsService.readShorts(req));
        }catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "쇼츠 댓글 작성")
    @PostMapping("/createComment")
    public ApiResult<CreateCommentDTO.Response> createComment(CreateCommentDTO.Request req){
        try{
            log.info("DTO ==  " + req);
            return ApiResult.success(shortsCommentService.createComment(req));
        }catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "쇼츠 댓글 조회")
    @PostMapping("/readComment")
    public ApiResult<ReadCommentDTO.AllResponse> readShorts(ReadCommentDTO.Request req){
        try{
            log.info("DTO ==  " + req);
            return ApiResult.success(shortsCommentService.readComment(req));
        }catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }
}
