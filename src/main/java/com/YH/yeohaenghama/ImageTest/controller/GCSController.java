package com.YH.yeohaenghama.ImageTest.controller;

import com.YH.yeohaenghama.ImageTest.dto.GCSRequest;
import com.YH.yeohaenghama.ImageTest.service.GCSService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class GCSController {

    private final GCSService gcsService;

    @PostMapping("/api/gcs/upload")
    public ResponseEntity<Void> objectUpload(@RequestParam("file") MultipartFile file) throws IOException {
        gcsService.uploadObject(file); // 수정된 부분
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
