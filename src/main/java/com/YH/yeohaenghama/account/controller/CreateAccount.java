package com.YH.yeohaenghama.account.controller;

import com.YH.yeohaenghama.account.dto.LoginRequest;
import com.YH.yeohaenghama.account.entity.Account;
import com.YH.yeohaenghama.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class CreateAccount {

    private final AccountService accountService;

    @PostMapping("/join")
    public String createAccount(@RequestBody LoginRequest request){
        Account account = new Account();
        account.setEmail(request.getEmail());
        account.setPw(request.getPw());
        account.setPhotoUrl(request.getPhotoUrl());
        account.setNickname(request.getNickname());
        accountService.createAccount(account);
        return "회원가입 성공";
    }
}
