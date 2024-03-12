package com.YH.yeohaenghama.domain.diary.controller;

import com.YH.yeohaenghama.common.apiResult.ApiResult;
import com.YH.yeohaenghama.domain.diary.dto.DiaryDTO;
import com.YH.yeohaenghama.domain.diary.service.DiaryService;
import com.google.protobuf.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/Diary")
public class DiaryController {
    private final DiaryService diaryService;


    @Operation(summary = "일기 저장")
    @PostMapping("/save")
    public ApiResult diarySave(@ModelAttribute DiaryDTO.Request diaryDTO){
        try{
            return ApiResult.success(diaryService.save(diaryDTO));

        } catch (Error e){
            return ApiResult.success(e.getMessage());
        }
        catch (NoSuchElementException e){
            return ApiResult.success(e.getMessage());
        } catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "일기 삭제")
    @PostMapping("/delete")
    public ApiResult diaryDelete(Long diaryId){
        try{
            diaryService.delete(diaryId);
            return ApiResult.success("일기 삭제 성공");
        }catch (NoSuchElementException e){
            return ApiResult.success(e.getMessage());
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }


    @Operation(summary = "일기 조회")
    @PostMapping("/show")
    public ApiResult diaryShow(Long diaryId){
        try{
            return ApiResult.success(diaryService.show(diaryId));
        }catch (NoSuchElementException e){
            return ApiResult.success(e.getMessage());
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }


    @Operation(summary = "모든 일기 조회")
    @PostMapping("/findAll")
    public ApiResult findAll(){
        try{
            return ApiResult.success(diaryService.findAll());
        }catch (NoSuchElementException e){
            return ApiResult.success(e.getMessage());
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }



}