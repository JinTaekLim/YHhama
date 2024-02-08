package com.YH.yeohaenghama.service;

import com.YH.yeohaenghama.dto.LoginRequest;
import com.YH.yeohaenghama.entity.Account;
import com.YH.yeohaenghama.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account createAccount(Account account){
        return accountRepository.save(account);
    }


    public Account login(LoginRequest req) {
        Optional<Account> optionalUser = accountRepository.findByEmail(req.getEmail());


        // loginId와 일치하는 User가 없으면 null return
        if(optionalUser.isEmpty()) {
            return null;
        }

        Account account = optionalUser.get();

        // 찾아온 User의 password와 입력된 password가 다르면 null return
        if(!account.getPw().equals(req.getPw())) {
            return null;
        }

        return account;
    }


//    public Account getLoginUserById(Long userId) {
//        if(userId == null) return null;
//
//        Optional<Account> optionalUser = accountRepository.findById(userId);
//        if(optionalUser.isEmpty()) return null;
//
//        return optionalUser.get();
//    }

}
