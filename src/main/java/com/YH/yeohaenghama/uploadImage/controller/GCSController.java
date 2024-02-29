package com.YH.yeohaenghama.uploadImage.controller;

import com.YH.yeohaenghama.uploadImage.service.GCSService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class GCSController {

    private final GCSService gcsService;

    @PostMapping("/api/gcs/upload")
    public ResponseEntity<String> objectUpload(@RequestParam("file") MultipartFile file, String fileName) {
        try {
            gcsService.uploadPhoto(file, fileName, "Profile_Image");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("사진 업로드 실패: " + e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("사진 업로드 실패: " + e.getMessage());
        }
    }

}
