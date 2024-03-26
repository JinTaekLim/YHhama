package com.YH.yeohaenghama.domain.report.controller;


import com.YH.yeohaenghama.common.apiResult.ApiResult;
import com.YH.yeohaenghama.domain.report.dto.ReportCommentDTO;
import com.YH.yeohaenghama.domain.report.dto.ReportCountDTO;
import com.YH.yeohaenghama.domain.report.dto.ReportDiaryDTO;
import com.YH.yeohaenghama.domain.report.dto.ReportReviewDTO;
import com.YH.yeohaenghama.domain.report.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @Operation(summary = "일기 신고")
    @PostMapping("/diary")
    public ApiResult<ReportCountDTO> diary(ReportDiaryDTO dto){
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
    public ApiResult<ReportCountDTO> review(ReportReviewDTO dto){
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
    public ApiResult<ReportCountDTO> comment(ReportCommentDTO dto){
        try{
            return ApiResult.success(reportService.commentReport(dto));
        }catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }
}
