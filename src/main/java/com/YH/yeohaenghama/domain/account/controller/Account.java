package com.YH.yeohaenghama.domain.account.controller;

import com.YH.yeohaenghama.domain.account.dto.AccountJoinDTO;
import com.YH.yeohaenghama.domain.account.dto.AccountLoginDTO;
import com.YH.yeohaenghama.domain.account.dto.AccountSavePlaceDTO;
import com.YH.yeohaenghama.domain.account.entity.AccountSavePlace;
import com.YH.yeohaenghama.domain.account.service.AccountSavePlaceService;
import com.YH.yeohaenghama.domain.account.service.AccountService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.*;


@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class Account {

    private final AccountService accountService;
    private final AccountSavePlaceService accountSavePlaceService;
    private final HttpSession httpSession;


    @Operation(summary = "아이디 중복 체크")
    @PostMapping("/emailDuplicateCheck")
    public ResponseEntity<String> emailDuplicateCheck(@RequestParam String email){
        if(accountService.emailDuplicateCheck(email) == false){
            return ResponseEntity.ok("사용 가능한 이메일 입니다.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("중복된 이메일 주소 입니다.");
    }


    @Operation(summary = "회원가입")
    @PostMapping("/join")
    public ResponseEntity<String> createAccount(@RequestBody AccountJoinDTO request) {
        if (accountService.emailDuplicateCheck(request.getEmail()) == false) {
            com.YH.yeohaenghama.domain.account.entity.Account account = new com.YH.yeohaenghama.domain.account.entity.Account();
            account.setEmail(request.getEmail());
            account.setPw(request.getPw());
            account.setPhotoUrl(request.getPhotoUrl());
            account.setNickname(request.getNickname());
            accountService.createAccount(account);
            return ResponseEntity.ok("회원가입 성공");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("중복된 이메일 주소 입니다.");
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AccountLoginDTO req) {
        com.YH.yeohaenghama.domain.account.entity.Account account = accountService.login(req);
        if (account != null) {
            httpSession.setAttribute("loggedInId", account);
            httpSession.setAttribute("nickname", account.getNickname());
            httpSession.setAttribute("AccountId", account.getId());
            return ResponseEntity.ok("로그인 성공 (ID = " + account.getId() + ")");
        } else {
            return ResponseEntity.badRequest().body("이메일 또는 비밀번호가 올바르지 않습니다.");
        }
    }

    @Operation(summary = "로그아웃")
    @GetMapping("/logout")
    public ResponseEntity<String> logout() {
        httpSession.removeAttribute("loggedInUser");
        return ResponseEntity.ok("로그아웃 성공");
    }

    @Operation(summary = "로그인 상태 확인")
    @GetMapping("/home")
    public ResponseEntity<String> home() {
        if (httpSession.getAttribute("loggedInUser") == null) {
            return ResponseEntity.ok("로그인 되어 있지 않음");
        } else {
            return ResponseEntity.ok("로그인 되어 있음");
        }
    }

    @Operation(summary = "장소 저장")
    @PostMapping("/savePlace")
    public ResponseEntity<String> savePlace(@RequestBody AccountSavePlaceDTO requestDto, @RequestParam Long accountId) {
        try {
            accountSavePlaceService.SavePlace(requestDto, accountId);
            return ResponseEntity.ok("장소 저장 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("장소 저장 실패: " + e.getMessage());
        }
    }

    @Operation(summary = "저장한 장소 조회")
    @GetMapping("/{accountId}")
    public List<AccountSavePlaceDTO> viewSavePlaces(@PathVariable Long accountId) {
        return accountSavePlaceService.ViewSavePlace(accountId);
    }

    @Operation(summary = "저장한 장소 삭제")
    @PostMapping("/deletePlace")
    public ResponseEntity<String> deletePlace(@RequestParam Long accountId, @RequestParam Long placeId){
        try {
            accountSavePlaceService.DeletePlace(accountId,placeId);
            return ResponseEntity.ok("저장된 장소 삭제 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("저장된 장소 삭제 실패: " + e.getMessage());
        }
    }
}
