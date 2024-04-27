package com.YH.yeohaenghama.domain.budget.controller;

import com.YH.yeohaenghama.common.apiResult.ApiResult;
import com.YH.yeohaenghama.domain.budget.dto.BudgetCreateDTO;
import com.YH.yeohaenghama.domain.budget.dto.BudgetDeleteDTO;
import com.YH.yeohaenghama.domain.budget.dto.BudgetShowDTO;
import com.YH.yeohaenghama.domain.budget.dto.ExpendituresAddDTO;
import com.YH.yeohaenghama.domain.budget.entity.Budget;
import com.YH.yeohaenghama.domain.budget.entity.Expenditures;
import com.YH.yeohaenghama.domain.budget.service.BudgetService;
import com.YH.yeohaenghama.domain.diary.dto.DiaryDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/Budget")
public class BudgetController {
    private final BudgetService budgetService;

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
            return ApiResult.success(budgetService.ExpendituresAdd(dto));
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
    @PostMapping("/show")
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

}
