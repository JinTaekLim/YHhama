package com.YH.yeohaenghama.controller;

import com.YH.yeohaenghama.dto.LoginRequest;
import com.YH.yeohaenghama.entity.Account;
import com.YH.yeohaenghama.service.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginAccount {

    @Autowired
    private HttpSession httpSession;
    @Autowired
    private AccountService accountService;
    @GetMapping("/")
    public String login() {
        // 세션에 로그인된 사용자 정보가 있는지 확인하여 로그인 여부를 판단하고, 홈페이지로 리다이렉트합니다.
        if (httpSession.getAttribute("loggedInUser") != null) {
            return "redirect:/home";
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(LoginRequest req, Model model) {
        Account account = accountService.login(req);
        if (account != null) {
            // 로그인 성공 시 세션에 사용자 정보를 저장합니다.
            httpSession.setAttribute("loggedInUser", account);
            httpSession.setAttribute("nickname", account.getNickname()); // 닉네임 저장

            return "redirect:/home"; // 로그인 성공 시 홈페이지로 리다이렉트
        } else {
            model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
            return "login"; // 로그인 실패 시 다시 로그인 페이지로 이동
        }
    }

    @GetMapping("/logout")
    public String logout() {
        // 로그아웃 시 세션에서 사용자 정보를 제거합니다.
        httpSession.removeAttribute("loggedInUser");
        return "redirect:/login"; // 로그아웃 후 로그인 페이지로 리다이렉트
    }

    @GetMapping("/home")
    public String home() {
        // 세션에 로그인된 사용자 정보가 없다면 로그인 페이지로 리다이렉트합니다.
        if (httpSession.getAttribute("loggedInUser") == null) {
            return "redirect:/";
        }
        return "home";
    }


}
