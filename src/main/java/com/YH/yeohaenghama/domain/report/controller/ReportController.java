package com.YH.yeohaenghama.domain.report.controller;


import com.YH.yeohaenghama.common.apiResult.ApiResult;
import com.YH.yeohaenghama.domain.diary.dto.CommentDTO;
import com.YH.yeohaenghama.domain.report.dto.*;
import com.YH.yeohaenghama.domain.report.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @Operation(summary = "일기 신고")
    @PostMapping("/diary")
    public ApiResult<ReportCountDTO> diary(@RequestBody ReportDiaryDTO dto){
        try{
            return ApiResult.success(reportService.diaryReport(dto));
        }catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "리뷰 신고")
    @PostMapping("/review")
    public ApiResult<ReportCountDTO> review(@RequestBody ReportReviewDTO dto){
        try{
            return ApiResult.success(reportService.reviewReport(dto));
        }catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }


    @Operation(summary = "댓글 신고")
    @PostMapping("/comment")
    public ApiResult<ReportCountDTO> comment(@RequestBody ReportCommentDTO dto){
        try{
            return ApiResult.success(reportService.commentReport(dto));
        }catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "신고된 일기 조회")
    @PostMapping("showDiary")
    public ApiResult<List<ReportDiaryDTO.Request>> showDiary(){
        try{
            return ApiResult.success(reportService.diaryReportList());
        }catch (NoSuchElementException e){
            return ApiResult.success(null);
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }


    @Operation(summary = "신고된 댓글 조회")
    @PostMapping("showComment")
    public ApiResult<List<ReportCommentDTO.Response>> showComment(){
        try{
            return ApiResult.success(reportService.commentReportList());
        }catch (NoSuchElementException e){
            return ApiResult.success(null);
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "신고된 리뷰 조회")
    @PostMapping("showReview")
    public ApiResult<List<ReportReviewDTO.Response>> showReview(){
        try{
            return ApiResult.success(reportService.reviewReportList());
        }catch (NoSuchElementException e){
            return ApiResult.success(null);
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }
}
