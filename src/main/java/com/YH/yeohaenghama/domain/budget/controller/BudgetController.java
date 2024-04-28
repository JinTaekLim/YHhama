package com.YH.yeohaenghama.domain.budget.controller;

import com.YH.yeohaenghama.common.apiResult.ApiResult;
import com.YH.yeohaenghama.domain.budget.dto.*;
import com.YH.yeohaenghama.domain.budget.entity.Budget;
import com.YH.yeohaenghama.domain.budget.entity.Expenditures;
import com.YH.yeohaenghama.domain.budget.service.BudgetService;
import com.YH.yeohaenghama.domain.budget.service.ExpendituresService;
import com.YH.yeohaenghama.domain.diary.dto.DiaryDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
    public ApiResult<BudgetCreateDTO.Response> budgetCreate(BudgetCreateDTO.Request dto){
        try{
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
    public ApiResult<ExpendituresAddDTO.Response> addExpenditures(ExpendituresAddDTO.Request dto){
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
    public ApiResult budgetDelete(BudgetDeleteDTO.Request dto){
        try{
            budgetService.budgetDelete(dto);
            return ApiResult.success(dto.getBudgetId(),"가계부 삭제 완료");
        } catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }
        catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "가계부 조회")
    @PostMapping("/budgetShow")
    public ApiResult<BudgetShowDTO.Response> budgetShow(BudgetShowDTO.Request dto){
        try{
            return ApiResult.success(budgetService.budgetShow(dto));
        } catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }
        catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "지출 금액 조회")
    @PostMapping("/expendituresShow")
    public ApiResult<List<ExpendituresShowDTO.Response>> expendituresShow(ExpendituresShowDTO.Request dto){
        try{
            return ApiResult.success(expendituresService.expendituresShow(dto));
        } catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }
        catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }


}
