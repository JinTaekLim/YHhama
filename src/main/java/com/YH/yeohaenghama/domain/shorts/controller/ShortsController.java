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
    @PutMapping("/updateShorts")
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

    @Operation(summary = "쇼츠 삭제")
    @DeleteMapping("/deleteShorts")
    public ApiResult<String> deleteShorts(@RequestParam("shortsId") Long shortsId,@RequestParam("accountId") Long accountId){
        try{
            return ApiResult.success(shortsService.deleteShorts(shortsId,accountId));
        }catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "쇼츠 좋아요", description = "description = \"state 값 0은 해당 쇼츠에 해당 유저가 좋아요를 클릭했는지 확인, 이 외의값(null,공백 허용) 좋아요 등록/취소\"" +
            "<p>좋아요 추가/삭제 한 번에 확인 가능. 만약 해당 쇼츠에 좋아요가 존재한다면 Enable값이 true로 반환, 아니라면 false반환</p>")
    @PostMapping("/likes")
    public ApiResult<LikesDTO.Response> likes(@RequestBody LikesDTO.Request req){
        try{
            return ApiResult.success(shortsService.likes(req));
        }catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }


    @Operation(summary = "쇼츠 조회")
    @GetMapping("/readShorts")
    public ApiResult<ReadShortsDTO.AllResponse> readShorts(@RequestParam(name = "numOfRows") Integer numOfRows,
                                                           @RequestParam(name = "page") Integer page){
        try{
            return ApiResult.success(shortsService.readShorts(numOfRows,page));
        }catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "쇼츠 댓글 작성")
    @PostMapping("/createComment")
    public ApiResult<CreateCommentDTO.Response> createComment(@RequestBody CreateCommentDTO.Request req){
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
    @GetMapping("/readComment")
    public ApiResult<ReadCommentDTO.AllResponse> readComment(@RequestParam(name = "shortsId") Long shortsId){
        try{
            return ApiResult.success(shortsCommentService.readComment(shortsId));
        }catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "쇼츠 댓글 수정")
    @PutMapping("/updateComment")
    public ApiResult<ReadCommentDTO.Response> updateShorts(@RequestBody UpdateCommentDTO.Request req){
        try{
            return ApiResult.success(shortsCommentService.updateComment(req));
        }catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }
    @Operation(summary = "쇼츠 댓글 삭제")
    @DeleteMapping("/deleteComment")
    public ApiResult<String> deleteComment(@RequestParam("commentId") Long commentId,@RequestParam("accountId") Long accountId){
        try{
            return ApiResult.success(shortsCommentService.deleteComment(commentId,accountId));
        }catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }
}
