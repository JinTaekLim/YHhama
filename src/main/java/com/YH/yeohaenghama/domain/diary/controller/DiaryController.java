package com.YH.yeohaenghama.domain.diary.controller;

import com.YH.yeohaenghama.common.apiResult.ApiResult;
import com.YH.yeohaenghama.common.apiResult.CommonResult;
import com.YH.yeohaenghama.domain.diary.dto.DiaryDTO;
import com.YH.yeohaenghama.domain.diary.dto.DiaryDetailDTO;
import com.YH.yeohaenghama.domain.diary.entity.DiaryDetail;
import com.YH.yeohaenghama.domain.diary.service.DiaryDetailService;
import com.YH.yeohaenghama.domain.diary.service.DiaryService;
import com.YH.yeohaenghama.domain.uploadImage.service.GCSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/Diary")
public class DiaryController {
    private final DiaryService diaryService;
    private final DiaryDetailService diaryDetailService;
    private final GCSService gcsService;


    //    @PostMapping("/save")
//    public ApiResult<DiaryDTO.Response> diarySave(@RequestBody DiaryDTO.Request req)   {
//        try {
//            DiaryDTO.Response response = diaryService.save(req);
//            return ApiResult.success(response);
//        }
//        catch (NoSuchElementException e) {
//            return ApiResult.notFound(e.getMessage());
//        }
//        catch (Exception e) {
//            return ApiResult.fail("");
//        }
//    }
    @PostMapping("/save")
    public ApiResult<DiaryDTO.Response> diarySave(@RequestParam("itinerary") Long itinerary,
                                                  @RequestParam("date") String date,
                                                  @RequestParam("title") String title,
                                                  @RequestParam("content") String content,
                                                  @RequestParam("photos") List<MultipartFile> photos) {
        try {

            if(diaryService.checkItinerary(itinerary) == false){
                log.info("해당 ID를 가진 일정이 존재하지 않습니다. : " + itinerary);
                throw new NoSuchElementException("해당 ID를 가진 일정이 존재하지 않습니다. : " + itinerary);
            }

            DiaryDTO.Request req = new DiaryDTO.Request();
            req.setItinerary(itinerary);
            req.setDate(date);
            req.setTitle(title);
            req.setContent(content);

            List<String> photoURLs = new ArrayList<>();

            int photoName = 1;

            for (MultipartFile photo : photos) {
                String photoUrl = gcsService.uploadPhoto(photo, "Diary" + photoName, "Diary/" + String.valueOf(req.getItinerary()));
                if (photoUrl != null) {
                    log.info("업로드 사진명 : " + photo.getOriginalFilename());
                    photoURLs.add(photoUrl);
                    photoName += 1;
                }
            }

            req.setPhotoURL(photoURLs);

            DiaryDTO.Response response = diaryService.save(req);
            return ApiResult.success(response);
        } catch (NoSuchElementException e) {
            return ApiResult.notFound(e.getMessage());
        } catch (Exception e) {
            log.info(e.getMessage());
            return ApiResult.fail("");
        }
    }







    @PostMapping("/Detailsave")
    public ApiResult<DiaryDetailDTO.Response> diarySave(@RequestParam("diary") Long diary,
                                                        @RequestParam("day") String day,
                                                        @RequestParam("content") String content,
                                                        @RequestParam("photos") List<MultipartFile> photos) {
        try {

            if(diaryDetailService.checkDiary(diary) == false){
                log.info("해당 ID를 가진 일정이 존재하지 않습니다. : " + diary);
                throw new NoSuchElementException("해당 ID를 가진 일정이 존재하지 않습니다. : " + diary);
            }
            DiaryDetailDTO.Request req = new DiaryDetailDTO.Request();
            req.setDiaryId(diary);
            req.setDay(day);
            req.setContent(content);

            List<String> photoURLs = new ArrayList<>();

            int photoName = 1;

            for (MultipartFile photo : photos) {
                String photoUrl = gcsService.uploadPhoto(photo, "Diary" + photoName, "Diary/" + req.getDiaryId() + "/Day" + req.getDay());
                if (photoUrl != null) {
                    log.info("업로드 사진명 : " + photo.getOriginalFilename());
                    photoURLs.add(photoUrl);
                    photoName += 1;
                }
            }

            req.setPhotoURL(photoURLs);

            DiaryDetailDTO.Response response = diaryDetailService.detailSave(req);
            return ApiResult.success(response);
        } catch (NoSuchElementException e) {
            return ApiResult.notFound(e.getMessage());
        } catch (Exception e) {
            log.info(e.getMessage());
            return ApiResult.fail("");
        }
    }


}