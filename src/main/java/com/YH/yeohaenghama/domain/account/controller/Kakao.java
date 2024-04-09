package com.YH.yeohaenghama.domain.account.controller;

import com.YH.yeohaenghama.common.apiResult.ApiResult;
import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.account.service.KakaoService;
import com.google.api.client.util.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("api/kakao")
public class Kakao {
    private String client_id = "a9d1711e66ed62d5be76957294ab0a9f";

    @Autowired
    private KakaoService kakaoService;

    @GetMapping("/login")
    public ApiResult<AccountShowDTO.Response> callback(@RequestParam("code") String code){
        try {
            log.info("code == " + code);
            String accessToken = kakaoService.getAccessTokenFromKakao(client_id, code);
            return ApiResult.success(kakaoService.getUserInfo(accessToken));
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }

    }
}