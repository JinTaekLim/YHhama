package com.YH.yeohaenghama.domain.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class html {

    @GetMapping("/upload-form")
    public String showUploadForm() {
        return "photo"; // photo.html 템플릿을 반환
    }

    @GetMapping("/test")
    public String test() {
        return "test"; // photo.html 템플릿을 반환
    }

    @GetMapping("/detail")
    public String detail() {
        return "detail"; // photo.html 템플릿을 반환
    }

}
