package com.YH.yeohaenghama.controller;

import com.YH.yeohaenghama.entity.Account;
import com.YH.yeohaenghama.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/CreateAccount")
@RequiredArgsConstructor
public class CreateAccount {
    final AccountService accountService;


    @PostMapping("/createAccount")
    public String createAccount(@RequestParam String email,
                                @RequestParam String pw,
                                @RequestParam(defaultValue = "/") String photoUrl,
                                @RequestParam String nickName){

        Account account = new Account();

        account.setEmail(email);
        account.setPw(pw);
        account.setPhotoUrl(photoUrl);
        account.setNickname(nickName);

        accountService.createAccount(account);

        System.out.println("회원가입 성공");
        return "/login";
    }
}
