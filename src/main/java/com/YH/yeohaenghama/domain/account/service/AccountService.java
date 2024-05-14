package com.YH.yeohaenghama.domain.account.service;

import com.YH.yeohaenghama.domain.GCDImage.service.GCSService;
import com.YH.yeohaenghama.domain.account.dto.AccountChangePwDTO;
import com.YH.yeohaenghama.domain.account.dto.AccountReportDTO;
import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.account.dto.AccountLoginDTO;
import com.YH.yeohaenghama.domain.account.entity.AccountReport;
import com.YH.yeohaenghama.domain.account.entity.AccountRole;
import com.YH.yeohaenghama.domain.account.repository.AccountReportRepository;
import com.YH.yeohaenghama.domain.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountReportRepository accountReportRepository;
    private final GCSService gcsService;

    public Account createAccount(Account account){
        return accountRepository.save(account);
    }


    public boolean emailDuplicateCheck(String email){
        Optional<Account> accountOptional = accountRepository.findByEmail(email);
        return accountOptional.isPresent();
    }


    public Account login(AccountLoginDTO req) {
        Optional<Account> optionalUser = accountRepository. findByEmail(req.getEmail());


        if(optionalUser.isEmpty()) { throw new NoSuchElementException("해당 이메일을 가진 계정을 찾을 수 없습니다."); }

        Account account = optionalUser.get();

        if(!account.getPw().equals(req.getPw())) { throw new NoSuchElementException("이메일 혹은 비밀번호를 확인해주세요."); }

        if(account.getStop() != null){
            Date stopDate = account.getStop();
            Date nowDate = new Date();

            if (nowDate.after(stopDate)) {
                account.setStop(null);
                accountRepository.save(account);
            }
        }

        return account;
    }

    public String changePw(AccountChangePwDTO.Request dto){
        Optional<Account> accountOpt = accountRepository.findById(dto.getAccountId());
        if (accountOpt.isEmpty()){
            throw new NoSuchElementException("해당 이메일을 가진 계정을 찾을 수 없습니다.");
        }
        Account account = accountOpt.get();

        if(!account.getPw().equals(dto.getOldPw())) throw new NoSuchElementException("기존 비밀번호가 일치하지 않습니다. ");

        account.setPw(dto.getNewPw());
        accountRepository.save(account);

        return "비밀번호 변경 성공";
    }


    public String randomChangePw(String email){
        Optional<Account> optionalAccount = accountRepository.findByEmail(email);
        if (optionalAccount.isEmpty()){
            throw new NoSuchElementException("해당 이메일을 가진 계정을 찾을 수 없습니다.");
        }
        Account account = optionalAccount.get();
        account.setPw(generateRandomString(10));
        accountRepository.save(account);
        return account.getPw();
    }

    public static String generateRandomString(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    public AccountShowDTO.Response update(AccountShowDTO.Request dto) throws IOException {
        Optional<Account> accountOtp = accountRepository.findById(dto.getId());
        if(accountOtp.isEmpty()){
            throw new NoSuchElementException("해당 Id값을 가진 유저가 존재하지 않습니다. ");
        }

        Account account = accountOtp.get();

        if(dto.getNickname() != null) {
            account.setNickname(dto.getNickname());
        }

        if(dto.getPhoto() != null) {
            log.info("삭제");
            gcsService.delete("Profile_Image/" + account.getEmail());
            String photoUrl = gcsService.uploadPhoto(dto.getPhoto(), String.valueOf(dto.getPhoto()) , "Profile_Image/"+account.getEmail());

            account.setPhotoUrl(photoUrl);
        }

        accountRepository.save(account);

        AccountShowDTO.Response response = new AccountShowDTO.Response(account.getId(), account.getNickname(), account.getPhotoUrl(), account.getRole());

        return response;
    }


    public String delete(Long accountId,String password){
        Optional<Account> accountOpt = accountRepository.findById(accountId);
        if (accountOpt.isEmpty()) throw new NoSuchElementException("해당 ID를 가진 유저가 존재하지 않습니다.");
        Account account = accountOpt.get();

        if(!account.getPw().equals(password)) throw new NoSuchElementException("비밀번호가 일치하지 않습니다.");

        accountRepository.deleteById(accountId);
        return "회원 삭제 성공";
    }

    public AccountReportDTO.Response warning(AccountReportDTO.Request dto){
        Optional<Account> adminOpt = accountRepository.findById(dto.getAdminId());

        if (adminOpt.get() == null || adminOpt.get().getRole() == AccountRole.ACCOUNT) { throw new NoSuchElementException("해당 ID를 가진 관리자 계정이 존재하지 않습니다."); }

        Optional<Account> accountOpt = accountRepository.findById(dto.getAccountId());

        if(accountOpt.isEmpty()) throw new NoSuchElementException("해당 ID를 가진 유저가 존재하지 않습니다.");

        Account account = accountOpt.get();


        int reportCount = accountReportRepository.findByAccountId(dto.getAccountId()).size();

        if((reportCount+1) % 3 == 0){ account.setStop(getAfterDay(7)); }

        accountReportRepository.save(AccountReportDTO.toEntity(account));


        AccountReportDTO.Response response = new AccountReportDTO.Response().fromEntity(account);

        return response;
    }

    public AccountReportDTO.Response stop(AccountReportDTO.stop dto){
        Optional<Account> adminOpt = accountRepository.findById(dto.getAdminid());

        if (adminOpt.get() == null || adminOpt.get().getRole() == AccountRole.ACCOUNT) { throw new NoSuchElementException("해당 ID를 가진 관리자 계정이 존재하지 않습니다."); }

        Optional<Account> accountOpt = accountRepository.findById(dto.getAccountId());

        if(accountOpt.isEmpty()) throw new NoSuchElementException("해당 ID를 가진 유저가 존재하지 않습니다.");

        Account account = accountOpt.get();

        account.setStop(getAfterDay(dto.getDay()));

        return new AccountReportDTO.Response().fromEntity(account);
    }

    public Date getAfterDay(int day){
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, day);

        return calendar.getTime();
    }

}
