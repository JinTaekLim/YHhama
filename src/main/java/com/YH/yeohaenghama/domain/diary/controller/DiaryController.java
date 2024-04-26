package com.YH.yeohaenghama.domain.diary.controller;

import com.YH.yeohaenghama.common.apiResult.ApiResult;
import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.diary.dto.*;
import com.YH.yeohaenghama.domain.diary.entity.Comment;
import com.YH.yeohaenghama.domain.diary.entity.Diary;
import com.YH.yeohaenghama.domain.diary.service.CommentService;
import com.YH.yeohaenghama.domain.diary.service.DiaryService;
import com.google.protobuf.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
    private final CommentService commentService;


    @Operation(summary = "일기 저장")
    @PostMapping("/save")
    public ApiResult diarySave(@ModelAttribute DiaryDTO.Request diaryDTO){
        try{
            log.info(String.valueOf(diaryDTO));
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
    public ApiResult diaryDelete(@RequestParam Long diaryId){
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
    public ApiResult diaryShow(@RequestParam Long diaryId){
        try{
            return ApiResult.success(diaryService.show(diaryId));
        }catch (NoSuchElementException e){
            return ApiResult.success(e.getMessage());
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "특정 유저의 모든 일기 조회")
    @PostMapping("/findAccountDiary")
    public ApiResult<List<DiaryShowDTO.AccountResponse>> findAccountDiary(@RequestParam Long accountId){
        try{
            return ApiResult.success(diaryService.findAccountDiary(accountId));
        }catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
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

    @Operation(summary = "특정 장소가 포함된 모든 일기 조회")
    @PostMapping("/findInPlace")
    public ApiResult<List<DiaryShowInPlaceDTO.Response>> findInPlace(@RequestBody DiaryShowInPlaceDTO.Request dto){
        try{
            return ApiResult.success(diaryService.findInPlace(dto));
        }catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }


    @Operation(summary = "일기 수정")
    @PostMapping("/update")
    public ApiResult<DiaryDTO.Response> update(@RequestParam Long diaryId,@RequestBody DiaryDTO.Request dto){
        try{
            return ApiResult.success(diaryService.updatae(diaryId,dto));
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }


    @Operation(summary = "댓글 작성")
    @PostMapping("/commentSave")
    public ApiResult<CommentDTO.Response> commentSave(@RequestBody CommentDTO.Request dto){
        try{
            return ApiResult.success(commentService.save(dto));
        } catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        } catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "댓글 삭제")
    @PostMapping("commentDelete")
    public ApiResult commentDelete(@RequestParam Long accountId,@RequestParam Long diaryId,@RequestParam Long commentId){
        try{
            return ApiResult.success(commentService.delete(accountId,diaryId,commentId));
        }catch (NoSuchElementException e){
            return ApiResult.success(e.getMessage());
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "댓글 수정")
    @PostMapping("commentUpdate")
    public ApiResult<CommentDTO.Response> commentUpdate(@RequestBody CommentDTO.Update dto){
        try{
            return ApiResult.success(commentService.update(dto));
        }catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "댓글 조회")
    @PostMapping("commentShow")
    public ApiResult<CommentShowDTO.Response> commentUpdate(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "조회할 일기", content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(name = "Example", value = "{\"DiaryId\":\"1\"}")
    ))@RequestBody CommentShowDTO dto){        try{
            return ApiResult.success(commentService.show(dto));
        }catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }


    @Operation(summary = "유저의 댓글 전체 조회")
    @PostMapping("accountCommentShow")
    public ApiResult<CommentShowDTO.Response> accountCommentShow(@RequestParam Long accountId){
        try{
            return ApiResult.success(commentService.show(accountId));
        }catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }
}