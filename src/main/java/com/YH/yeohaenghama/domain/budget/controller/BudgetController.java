package com.YH.yeohaenghama.domain.budget.controller;

import com.YH.yeohaenghama.common.apiResult.ApiResult;
import com.YH.yeohaenghama.domain.budget.dto.*;
import com.YH.yeohaenghama.domain.budget.service.BudgetService;
import com.YH.yeohaenghama.domain.budget.service.ExpendituresService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/Budget")
public class BudgetController {
    private final BudgetService budgetService;
    private final ExpendituresService expendituresService;

    @Operation(summary = "가계부 생성")
    @PostMapping("/create")
    public ApiResult<BudgetCreateDTO.Response> budgetCreate(@RequestBody BudgetCreateDTO.Request dto){
        try{
            log.info("DTO ==== " + dto);
            return ApiResult.success(budgetService.budgetCreate(dto));
        } catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }
        catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "가계부 지출 추가")
    @PostMapping("/addExpenditures")
    public ApiResult<ExpendituresAddDTO.Response> addExpenditures(@RequestBody ExpendituresAddDTO.Request dto){
        try{
            log.info("dto = "+dto);
            return ApiResult.success(expendituresService.expendituresAdd(dto));
        } catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }
        catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "가계부 삭제")
    @PostMapping("/delete")
    public ApiResult<String> budgetDelete(@RequestBody BudgetDeleteDTO.Request dto){
        try{
            log.info("DTO ==== " + dto);
            return ApiResult.success(budgetService.budgetDelete(dto));
        } catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }
        catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "지출 금액 단일 삭제")
    @PostMapping("/expendituresDeleteOne")
    public ApiResult<String> expendituresDeleteOne(@RequestBody ExpendituresDeleteDTO.RequestDeleteOne dto){
        try{
            log.info("DTO ==== " + dto);
            return ApiResult.success(expendituresService.expendituresDeleteOne(dto));
        } catch (NoSuchElementException e){
            return ApiResult.notFound(e.getMessage());
        }
        catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "가계부 조회")
    @PostMapping("/budgetShow")
    public ApiResult<BudgetShowDTO.Response> budgetShow(@RequestBody BudgetShowDTO.Request dto){
        try{
            log.info("DTO ==== " + dto);
            return ApiResult.success(budgetService.budgetShow(dto));
        } catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }
        catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }


    @Operation(summary = "특정 지출 조회")
    @PostMapping("/expendituresShow")
    public ApiResult<ExpendituresAddDTO.getRequest> expendituresShow(@RequestParam Long expenditureId){
        try{
            return ApiResult.success(expendituresService.expendituresShow(expenditureId));
        } catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }
        catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }


    @Operation(summary = "가계부 통계 조회")
    @PostMapping("/statistics")
    public ApiResult<BudgetStatisticsDTO.Response> budgetStatistics(@RequestBody BudgetStatisticsDTO.Request dto){
        try{
            log.info("DTO ==== " + dto);
            return ApiResult.success(budgetService.budgetStatistics(dto));
        } catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }
        catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "가계부 정산")
    @PostMapping("/calculate")
    public ApiResult<BudgetCalculateDTO.Response> calculate(@RequestBody BudgetCalculateDTO.Request dto){
        try{
            log.info("DTO ==== " + dto);
            return ApiResult.success(expendituresService.calculate(dto));
        } catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }
        catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }


    @Operation(summary = "영수증 스캔")
    @PostMapping("/scanReceipt")
    public ApiResult<ResponseEntity<String>> calculate(@RequestParam("file") MultipartFile file){
        try{
            return ApiResult.success(expendituresService.scanReceipt(file));
        } catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }
        catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }





}
