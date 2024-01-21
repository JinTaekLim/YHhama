package com.YH.yeohaenghama.service;

import com.YH.yeohaenghama.entity.Account;
import com.YH.yeohaenghama.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account createAccount(Account account){
        return accountRepository.save(account);
    }
}
