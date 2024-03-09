package com.YH.yeohaenghama.domain.diary.controller;

import com.YH.yeohaenghama.common.apiResult.ApiResult;
import com.YH.yeohaenghama.domain.diary.dto.DiaryDTO;
import com.YH.yeohaenghama.domain.diary.dto.DiaryDetailDTO;
import com.YH.yeohaenghama.domain.diary.service.DiaryDetailService;
import com.YH.yeohaenghama.domain.diary.service.DiaryService;
import com.YH.yeohaenghama.domain.uploadImage.service.GCSService;
import com.google.protobuf.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/Diary")
public class DiaryController {
    private final DiaryService diaryService;
    private final DiaryDetailService diaryDetailService;
    private final GCSService gcsService;


    @Operation(summary = "일기 저장")
    @PostMapping("/test")
    public ApiResult diarySave(@ModelAttribute DiaryDTO.Request diaryDTO){
        try{
            diaryService.save(diaryDTO);

            return ApiResult.success("");

        } catch (NoSuchElementException e){
            return ApiResult.success(e.getMessage());
        } catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

}