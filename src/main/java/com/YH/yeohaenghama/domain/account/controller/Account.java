package com.YH.yeohaenghama.domain.account.controller;

import com.YH.yeohaenghama.common.apiResult.ApiResult;
import com.YH.yeohaenghama.domain.account.dto.AccountLoginDTO;
import com.YH.yeohaenghama.domain.account.dto.AccountSavePlaceDTO;
import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.account.service.AccountSavePlaceService;
import com.YH.yeohaenghama.domain.account.service.AccountService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import io.swagger.v3.oas.annotations.*;
import com.YH.yeohaenghama.domain.GCDImage.service.GCSService;
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


    @Operation(summary = "테스트 코드 Int")
    @PostMapping("/tsetIntInsert")
    public void testInt(int a){
        log.info("testIntInsert : "+a);
    }

    @Operation(summary = "테스트 코드 Integer")
    @PostMapping("/tsetIntegerInsert")
    public void testInt(Integer a){
        log.info("testIntegerInsert : "+a);
    }

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

        log.info(String.valueOf(file));
        try {
            if (email.isEmpty() || pw.isEmpty() || nickname.isEmpty() ) {
                return ApiResult.badRequest("누락된 데이터가 존재합니다.");
            }

            com.YH.yeohaenghama.domain.account.entity.Account account = new com.YH.yeohaenghama.domain.account.entity.Account();

            String photoUrl = gcsService.uploadPhoto(file, email, "Profile_Image/"+email);
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
    public ApiResult<AccountShowDTO.Response> login(@RequestBody AccountLoginDTO req) {
        try{
            if(req.getEmail()==null || req.getPw()==null){
                return ApiResult.badRequest("누락된 데이터가 존재합니다.");
            }
            com.YH.yeohaenghama.domain.account.entity.Account account = accountService.login(req);
            httpSession.setAttribute("loggedInId", account);
            httpSession.setAttribute("nickname", account.getNickname());
            httpSession.setAttribute("AccountId", account.getId());

            AccountShowDTO.Response accountShowDTO = new AccountShowDTO.Response(account.getId(), account.getEmail(), account.getPhotoUrl());

            return ApiResult.success(accountShowDTO);
        }
        catch (NoSuchElementException e){
            return ApiResult.success( null,"로그인 실패 : " + e.getMessage());
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
    public ApiResult deletePlace(@RequestParam Long accountId, @RequestParam Long placeId){
        try {
            accountSavePlaceService.DeletePlace(accountId,placeId);
            return ApiResult.success("저장된 장소 삭제 완료");
        } catch (IllegalArgumentException e){
            return ApiResult.success(accountId,e.getMessage());
        }
        catch (NoSuchElementException e) {
            return ApiResult.success(null, e.getMessage());
        } catch (Exception e) {
            return ApiResult.fail("저장된 장소 삭제 실패: " + e.getMessage());
        }
    }

    @Operation(summary = "비밀번호 무작위 변경")
    @PostMapping("/ChangePw")
    public ApiResult changePw(@RequestParam String email){
        try{
            return ApiResult.success("[성공] 새 비밀번호 : " + accountService.changePw(email));
        }
        catch (NoSuchElementException e){
            return ApiResult.success("비밀번호 변경 실패 : " + e.getMessage());
        }
        catch (Exception e){
            return ApiResult.fail("비밀번호 변경 실패 : " + e.getMessage());
        }

    }



    @Operation(summary = "프로필 편집")
    @PostMapping("/update")
    public ApiResult<AccountShowDTO.Response> update(AccountShowDTO.Request dto){
        try{
            return ApiResult.success(accountService.update(dto));
        }catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }
}
