package com.YH.yeohaenghama.domain.diary.controller;

import com.YH.yeohaenghama.common.apiResult.ApiResult;
import com.YH.yeohaenghama.domain.diary.dto.DiaryDTO;
import com.YH.yeohaenghama.domain.diary.dto.DiaryDetailDTO;
import com.YH.yeohaenghama.domain.diary.service.DiaryDetailService;
import com.YH.yeohaenghama.domain.diary.service.DiaryService;
import com.YH.yeohaenghama.domain.uploadImage.service.GCSService;
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
    @PostMapping("/save")
    public ApiResult<DiaryDTO.Response> diarySave(@RequestParam("itinerary") Long itinerary,
                                                  @RequestParam("date") String date,
                                                  @RequestParam("title") String title,
                                                  @RequestParam("content") String content,
                                                  @RequestParam("photos") List<MultipartFile> photos) {
        try {
            checkItineraryExistence(itinerary);

            DiaryDTO.Request req = createDiaryRequest(itinerary, date, title, content, photos);
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
    public ApiResult<DiaryDetailDTO.Response> diaryDetailSave(@RequestParam("diary") Long diary,
                                                              @RequestParam("day") String day,
                                                              @RequestParam("content") String content,
                                                              @RequestParam("photos") List<MultipartFile> photos) {
        try {
            checkDiaryExistence(diary);

            DiaryDetailDTO.Request req = createDiaryDetailRequest(diary, day, content, photos);
            DiaryDetailDTO.Response response = diaryDetailService.detailSave(req);
            return ApiResult.success(response);
        } catch (NoSuchElementException e) {
            return ApiResult.notFound(e.getMessage());
        } catch (Exception e) {
            log.info(e.getMessage());
            return ApiResult.fail("");
        }
    }

    @PostMapping("/deleteDiary")
    public ApiResult<DiaryDTO.Response> deleteDiary(@RequestParam("diaryId") Long diraryId){
        try{
            DiaryDTO.Response diary = diaryService.checkDiary(diraryId);
            diaryService.delete(diraryId);
            log.info(String.valueOf(diary));
            return ApiResult.success(diary);
        } catch (NoSuchElementException e){
            return ApiResult.notFound(e.getMessage());
        } catch (Exception e){
            e.getMessage();
            return ApiResult.fail("");
        }
    }










    private void checkItineraryExistence(Long itinerary) {
        if (!diaryService.checkItinerary(itinerary)) {
            log.info("해당 ID를 가진 일정이 존재하지 않습니다. : " + itinerary);
            throw new NoSuchElementException("해당 ID를 가진 일정이 존재하지 않습니다. : " + itinerary);
        }
    }

    private void checkDiaryExistence(Long diary) {
        if (!diaryDetailService.checkDiary(diary)) {
            log.info("해당 ID를 가진 일기가 존재하지 않습니다. : " + diary);
            throw new NoSuchElementException("해당 ID를 가진 일기가 존재하지 않습니다. : " + diary);
        }
    }

    private DiaryDTO.Request createDiaryRequest(Long itinerary, String date, String title, String content, List<MultipartFile> photos) throws IOException {
        DiaryDTO.Request req = new DiaryDTO.Request();
        req.setItinerary(itinerary);
        req.setDate(date);
        req.setTitle(title);
        req.setContent(content);
        req.setPhotoURL(uploadPhotosAndGetUrls(photos, "","Diary"));
        return req;
    }

    private DiaryDetailDTO.Request createDiaryDetailRequest(Long diary, String day, String content, List<MultipartFile> photos) throws IOException {
        DiaryDetailDTO.Request req = new DiaryDetailDTO.Request();
        req.setDiaryId(diary);
        req.setDay(day);
        req.setContent(content);
        req.setPhotoURL(uploadPhotosAndGetUrls(photos, "Day","Diary/Day" + day));
        return req;
    }

    private List<String> uploadPhotosAndGetUrls(List<MultipartFile> photos,String fileName, String Folder) throws IOException {
        List<String> photoURLs = new ArrayList<>();
        int photoName = 1;
        for (MultipartFile photo : photos) {
            String photoUrl = gcsService.uploadPhoto(photo, "Diary" + fileName + photoName, Folder);
            if (photoUrl != null) {
                log.info("업로드 사진명 : " + photo.getOriginalFilename());
                photoURLs.add(photoUrl);
                photoName++;
            }
        }
        return photoURLs;
    }


}