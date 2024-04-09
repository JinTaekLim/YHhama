package com.YH.yeohaenghama.domain.account.service;

import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.account.repository.AccountRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoService {

    private final AccountRepository accountRepository;

    public String getAccessTokenFromKakao(String client_id, String code) throws IOException {

        String reqURL = "https://kauth.kakao.com/oauth/token?grant_type=authorization_code&client_id=" + client_id + "&code=" + code;
        URL url = new URL(reqURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");


        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String line = "";
        String result = "";

        while ((line = br.readLine()) != null) {
            result += line;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonMap = objectMapper.readValue(result, new TypeReference<Map<String, Object>>() {
        });

        log.info("Response Body : " + result);

        String accessToken = (String) jsonMap.get("access_token");
        String refreshToken = (String) jsonMap.get("refresh_token");
        String scope = (String) jsonMap.get("scope");


        log.info("Access Token : " + accessToken);
        log.info("Refresh Token : " + refreshToken);
        log.info("Scope : " + scope);


        return accessToken;
    }


    public AccountShowDTO.Response getUserInfo(String access_Token) throws IOException {
        HashMap<String, Object> userInfo = new HashMap<String, Object>();

        String reqURL = "https://kapi.kakao.com/v2/user/me";
        URL url = new URL(reqURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + access_Token);

        int responseCode = conn.getResponseCode();
        System.out.println("responseCode : " + responseCode);

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String line = "";
        String result = "";

        while ((line = br.readLine()) != null) {
            result += line;
        }

        log.info("Response Body : " + result);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonMap = objectMapper.readValue(result, new TypeReference<Map<String, Object>>() {});

        Map<String, Object> properties = (Map<String, Object>) jsonMap.get("properties");
        Map<String, Object> kakao_account = (Map<String, Object>) jsonMap.get("kakao_account");

        String id = "K" + jsonMap.get("id");
        String nickname = properties.get("nickname").toString();
        String profileImage = properties.get("profile_image") != null ? properties.get("profile_image").toString() : "default_profile_image_url";
        String email = kakao_account.get("email").toString();

        Optional<Account> accountOpt = accountRepository.findByexternalId(id);
        if(accountOpt.isEmpty()){
            log.info("// 계정이 존재하지 않을 때");

            Account account = new Account();
            account.setId((Long) jsonMap.get("id"));
            account.setEmail(email);
            account.setPhotoUrl(profileImage);
            account.setExternalId(id);
            account.setNickname(nickname);

            accountRepository.save(account);
        } else {
            log.info("// 계정이 이미 존재할 때");
            Account existingAccount = accountOpt.get();

            if (!existingAccount.getPhotoUrl().equals(profileImage)) {
                log.info("// 프로필 사진이 변경된 경우");

                existingAccount.setPhotoUrl(profileImage);
                accountRepository.save(existingAccount);
            }
        }

        accountOpt = accountRepository.findByexternalId(id);
        AccountShowDTO.Response accountShowDTO = new AccountShowDTO.Response(accountOpt.get().getId(), nickname, profileImage);

        return accountShowDTO;
    }


}

