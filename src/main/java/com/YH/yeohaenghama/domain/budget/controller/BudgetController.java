package com.YH.yeohaenghama.domain.budget.controller;

import com.YH.yeohaenghama.common.apiResult.ApiResult;
import com.YH.yeohaenghama.domain.budget.dto.BudgetCreateDTO;
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
    public ApiResult budgetCreate(BudgetCreateDTO.Request dto){
        try{
            budgetService.budgetCreate(dto);
            return ApiResult.success(null);
        } catch (NoSuchElementException e){
            return ApiResult.success(e.getMessage());
        }
        catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

}
