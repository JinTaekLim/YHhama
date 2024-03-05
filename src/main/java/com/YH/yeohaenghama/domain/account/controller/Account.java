package com.YH.yeohaenghama.domain.account.controller;

import com.YH.yeohaenghama.common.apiResult.ApiResult;
import com.YH.yeohaenghama.domain.account.dto.AccountLoginDTO;
import com.YH.yeohaenghama.domain.account.dto.AccountSavePlaceDTO;
import com.YH.yeohaenghama.domain.account.service.AccountSavePlaceService;
import com.YH.yeohaenghama.domain.account.service.AccountService;
import com.google.protobuf.Api;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import io.swagger.v3.oas.annotations.*;
import com.YH.yeohaenghama.domain.uploadImage.service.GCSService;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
@Slf4j
@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class Account {

    private final AccountService accountService;
    private final AccountSavePlaceService accountSavePlaceService;
    private final HttpSession httpSession;
    private final GCSService gcsService;


    @Operation(summary = "아이디 중복 체크")
    @PostMapping("/emailDuplicateCheck")
    public ApiResult emailDuplicateCheck(@RequestParam String email){
        if(accountService.emailDuplicateCheck(email) == false){
            return ApiResult.success(email,"사용 가능한 이메일 주소 입니다.");
        }
        return ApiResult.fail("중복되는 이메일 주소가 있습니다.");
    }

    @Operation(summary = "회원가입")
    @PostMapping("/join")
    public ApiResult createAccount(@RequestParam("email") String email,
                                     @RequestParam("pw") String pw,
                                     @RequestParam("nickname") String nickname,
                                     @RequestParam(value = "file",required = false) MultipartFile file) {

        try {
            if (email.isEmpty() || pw.isEmpty() || nickname.isEmpty() ) {
                return ApiResult.badRequest("누락된 데이터가 존재합니다.");
            }

            com.YH.yeohaenghama.domain.account.entity.Account account = new com.YH.yeohaenghama.domain.account.entity.Account();

            String photoUrl = gcsService.uploadPhoto(file, email, "Profile_Image");
            account.setEmail(email);
            account.setPw(pw);
            account.setNickname(nickname);
            account.setPhotoUrl(photoUrl);
            accountService.createAccount(account);

            log.info(email);
            return ApiResult.success(email,"회원 가입 성공");
        } catch (MultipartException e) {
            return ApiResult.badRequest("파일 형식이 잘못되었습니다.");
        } catch (IOException e) {
            return ApiResult.fail("파일 업로드 중 오류가 발생했습니다");
        } catch (Exception e) {
            return ApiResult.fail("회원 가입 실패");
        }
    }



    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ApiResult<AccountLoginDTO> login(@RequestBody AccountLoginDTO req) {
        try{
            if(req.getEmail()==null || req.getPw()==null){
                return ApiResult.badRequest("누락된 데이터가 존재합니다.");
            }
            com.YH.yeohaenghama.domain.account.entity.Account account = accountService.login(req);
            httpSession.setAttribute("loggedInId", account);
            httpSession.setAttribute("nickname", account.getNickname());
            httpSession.setAttribute("AccountId", account.getId());
            return ApiResult.success(req);
        }
        catch (NoSuchElementException e){
            return ApiResult.success( req,"로그인 실패 : " + e.getMessage());
        }
        catch (Exception e) {
            return ApiResult.fail("");
        }
    }

    @Operation(summary = "로그아웃")
    @GetMapping("/logout")
    public ApiResult logout() {
        try{
            httpSession.removeAttribute("loggedInUser");
            return ApiResult.success("로그아웃 성공");
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "로그인 상태 확인")
    @GetMapping("/home")
    public ApiResult home() {
        try {
            if (httpSession.getAttribute("loggedInUser") == null) {
                return ApiResult.success("로그인 되어 있지 않음");
            } else {
                return ApiResult.success("로그인 되어 있음");
            }
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "장소 저장")
    @PostMapping("/savePlace")
    public ApiResult<AccountSavePlaceDTO> savePlace(@RequestBody AccountSavePlaceDTO requestDto, @RequestParam Long accountId) {
        try {
            boolean place = accountSavePlaceService.SavePlace(requestDto, accountId);
            if(place == false){
                return ApiResult.success(requestDto,"이미 저장되어 있는 장소입니다.");
            }
            return ApiResult.success(requestDto,"장소 저장 완료");
        }
        catch (Exception e) {
            return ApiResult.fail("장소 저장 실패: " + e.getMessage());
        }
    }

    @Operation(summary = "저장한 장소 조회")
    @GetMapping("/{accountId}")
    public ApiResult<List<AccountSavePlaceDTO>> viewSavePlaces(@PathVariable Long accountId) {
        try{
            return ApiResult.success(accountSavePlaceService.ViewSavePlace(accountId));
        }
        catch (IllegalArgumentException e){
            return ApiResult.success(null,e.getMessage());
        }
        catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
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

    @Operation(summary = "비밀번호 무작위 변경")
    @PostMapping("/ChangePw")
    public ResponseEntity changePw(@RequestParam String email){
        try{
            return ResponseEntity.ok("[성공] 새 비밀번호 : " + accountService.changePw(email));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호 변경 실패 : " + e.getMessage());
        }

    }

}
