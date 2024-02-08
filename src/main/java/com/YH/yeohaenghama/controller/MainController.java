package com.YH.yeohaenghama.controller;

import com.YH.yeohaenghama.dto.LoginRequest;
import com.YH.yeohaenghama.entity.Account;
import com.YH.yeohaenghama.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class MainController {
    @GetMapping("/createAccount")
    public String createAccount() {
        return "createAccount";
    }


}
