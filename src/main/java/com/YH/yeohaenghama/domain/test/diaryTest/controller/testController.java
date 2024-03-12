package com.YH.yeohaenghama.domain.test.diaryTest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class testController {

    @GetMapping("/createAccount")
    public String showAHtml() {
        return "photo";
    }
}
