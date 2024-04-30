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

    @Operation(summary = "특정 유저 가계부 지출 추가")
    @PostMapping("/addExpendituresGroupAdd")
    public ApiResult<String> addExpendituresGroupAdd(ExpendituresGroupAddDTO.Request dto){
        try{
            log.info("dto = "+dto);
            return ApiResult.success(expendituresService.expendituresGroupAdd(dto));
        } catch (NoSuchElementException e){
            return ApiResult.notFound(e.getMessage());
        }
        catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }
//
//    @Operation(summary = "가계부 유저 추가")
//    @PostMapping("/addAccount")
//    public ApiResult<String> addAccount(BudgetAccountAddDTO.Request dto){
//        try{
//            return ApiResult.success(budgetService.budgetAddAccount(dto));
//        } catch (NoSuchElementException e){
//            return ApiResult.notFound(e.getMessage());
//        }
//        catch (Exception e){
//            return ApiResult.fail(e.getMessage());
//        }
//    }



    @Operation(summary = "가계부 삭제")
    @PostMapping("/delete")
    public ApiResult<String> budgetDelete(BudgetDeleteDTO.Request dto){
        try{
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
    public ApiResult<String> expendituresDeleteOne(ExpendituresDeleteDTO.RequestDeleteOne dto){
        try{
            return ApiResult.success(expendituresService.expendituresDeleteOne(dto));
        } catch (NoSuchElementException e){
            return ApiResult.notFound(e.getMessage());
        }
        catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "지출 금액 날짜별 삭제")
    @PostMapping("/expendituresDeleteDay")
    public ApiResult<String> expendituresDeleteDay(ExpendituresDeleteDTO.RequestDeleteDay dto){
        try{
            return ApiResult.success(expendituresService.expendituresDeleteDay(dto));
        } catch (NoSuchElementException e){
            return ApiResult.notFound(e.getMessage());
        }
        catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "지출 금액 전체 삭제")
    @PostMapping("/expendituresDeleteBudget")
    public ApiResult<String> expendituresDeleteBudget(ExpendituresDeleteDTO.RequestDeleteBudget dto){
        try{
            return ApiResult.success(expendituresService.expendituresDeleteBudget(dto));
        } catch (NoSuchElementException e){
            return ApiResult.notFound(e.getMessage());
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

    @Operation(summary = "공동 지출 금액 조회")
    @PostMapping("/expendituresGroupShow")
    public ApiResult<List<ExpendituresGroupShowDTO.Response>> expendituresGroupShow(ExpendituresGroupShowDTO.Request dto){
        try{
            return ApiResult.success(expendituresService.expendituresGroupShow(dto));
        } catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }
        catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }






}
