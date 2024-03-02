package com.YH.yeohaenghama.domain.diary.controller;

import com.YH.yeohaenghama.common.apiResult.ApiResult;
import com.YH.yeohaenghama.domain.diary.dto.DiaryDTO;
import com.YH.yeohaenghama.domain.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/Diary")
public class DiaryController {
    private final DiaryService diaryService;

    @PostMapping("/save")
    public ApiResult<DiaryDTO.Response> diarySave(@RequestBody DiaryDTO.Request req){
        log.info("DiaryDTO : " + req);
        DiaryDTO.Response response = diaryService.save(req);
        return ApiResult.success(response);
    }
}
