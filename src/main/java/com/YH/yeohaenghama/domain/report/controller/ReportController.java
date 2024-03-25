package com.YH.yeohaenghama.domain.report.controller;


import com.YH.yeohaenghama.common.apiResult.ApiResult;
import com.YH.yeohaenghama.domain.report.dto.ReportDiaryDTO;
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

    @Operation(summary = "신고", description = "typeId : 1 ( 일기 ) , 2 ( 리뷰 ) <br/> 위 번호 중 신고할 페이지의 ID를 입력해주세요.")
    @PostMapping("/diary")
    public ApiResult diary(ReportDiaryDTO dto){
        try{
            reportService.report(dto);
            return ApiResult.success(dto);
        }catch (NoSuchElementException e){
            return ApiResult.success(e.getMessage());
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }
}
