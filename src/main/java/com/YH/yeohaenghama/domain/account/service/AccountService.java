package com.YH.yeohaenghama.domain.account.service;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.account.dto.AccountLoginDTO;
import com.YH.yeohaenghama.domain.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;

    public Account createAccount(Account account){
        return accountRepository.save(account);
    }


    public boolean emailDuplicateCheck(String email){
        Optional<Account> accountOptional = accountRepository.findByEmail(email);
        return accountOptional.isPresent();
    }


    public Account login(AccountLoginDTO req) {
//        if(req ==null || req.getEmail().isEmpty() || req.getPw().i){
//            log.info("1");
//            throw new BadRequestException("누락된 데이터가 있습니다.");
//        }
        Optional<Account> optionalUser = accountRepository. findByEmail(req.getEmail());


        if(optionalUser.isEmpty()) {
            log.info("2");
            throw new NoSuchElementException("해당 이메일을 가진 계정을 찾을 수 없습니다.");
        }

        Account account = optionalUser.get();

        if(!account.getPw().equals(req.getPw())) {
            throw new NoSuchElementException("이메일 혹은 비밀번호를 확인해주세요.");
        }

        return account;
    }

    public String changePw(String email){
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


}
