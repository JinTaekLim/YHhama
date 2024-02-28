package com.YH.yeohaenghama.domain.test.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

    @PostMapping("/upload")
    public void uploadImage(@RequestBody String base64Image) {
        System.out.println("Received Base64 encoded image:");
        System.out.println(base64Image);
    }
}
