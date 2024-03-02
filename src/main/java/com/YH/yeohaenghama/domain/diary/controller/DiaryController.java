package com.YH.yeohaenghama.domain.diary.controller;

import com.YH.yeohaenghama.common.apiResult.ApiResult;
import com.YH.yeohaenghama.common.apiResult.CommonResult;
import com.YH.yeohaenghama.domain.diary.dto.DiaryDTO;
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
        DiaryDTO.Request req = new DiaryDTO.Request();
        req.setItinerary(itinerary);
        req.setDate(date);
        req.setTitle(title);
        req.setContent(content);

        List<String> photoURLs = new ArrayList<>();

        for (MultipartFile photo : photos) {
            String photoUrl = gcsService.uploadPhoto(photo, photo.getOriginalFilename(), String.valueOf(req.getItinerary()));
            if(photoUrl != null){
                log.info("업로드 사진명 : " + photo.getOriginalFilename());
                photoURLs.add(photoUrl);
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



    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("photos") List<MultipartFile> photos,
                                   @RequestParam("title") String foloder) throws IOException {

        for (MultipartFile photo : photos) {

            String photoUrl = gcsService.uploadPhoto(photo, photo.getOriginalFilename(), foloder);
            if(photoUrl != null){
                log.info("업로드 사진명 : " + photo.getOriginalFilename());

            }

        }

        return "파일 업로드 완료";
    }
}