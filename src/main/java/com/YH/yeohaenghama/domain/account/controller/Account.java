package com.YH.yeohaenghama.domain.account.controller;

import com.YH.yeohaenghama.domain.account.dto.AccountJoinDTO;
import com.YH.yeohaenghama.domain.account.dto.AccountLoginDTO;
import com.YH.yeohaenghama.domain.account.service.AccountService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class Account {

    private final AccountService accountService;
    private final HttpSession httpSession;

    @PostMapping("/join")
    public ResponseEntity<String> createAccount(@RequestBody AccountJoinDTO request) {
        com.YH.yeohaenghama.domain.account.entity.Account account = new com.YH.yeohaenghama.domain.account.entity.Account();
        account.setEmail(request.getEmail());
        account.setPw(request.getPw());
        account.setPhotoUrl(request.getPhotoUrl());
        account.setNickname(request.getNickname());
        accountService.createAccount(account);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AccountLoginDTO req) {
        com.YH.yeohaenghama.domain.account.entity.Account account = accountService.login(req);
        if (account != null) {
            httpSession.setAttribute("loggedInUser", account);
            httpSession.setAttribute("nickname", account.getNickname());
            return ResponseEntity.ok("로그인 성공");
        } else {
            return ResponseEntity.badRequest().body("아이디 또는 비밀번호가 올바르지 않습니다.");
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout() {
        httpSession.removeAttribute("loggedInUser");
        return ResponseEntity.ok("로그아웃 성공");
    }

    @GetMapping("/home")
    public ResponseEntity<String> home() {
        if (httpSession.getAttribute("loggedInUser") == null) {
            return ResponseEntity.ok("로그인 되어 있지 않음");
        } else {
            return ResponseEntity.ok("로그인 되어 있음");
        }
    }
}
