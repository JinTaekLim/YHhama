package com.YH.yeohaenghama.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String login(){return "login";}
    @GetMapping("/createAccount")
    public String createAccount(){return "createAccount";}
}
