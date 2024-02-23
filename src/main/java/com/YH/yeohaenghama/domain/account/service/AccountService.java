package com.YH.yeohaenghama.domain.account.service;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.account.dto.AccountLoginDTO;
import com.YH.yeohaenghama.domain.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
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
        Optional<Account> optionalUser = accountRepository. findByEmail(req.getEmail());

        if(optionalUser.isEmpty()) {
            return null;
        }

        Account account = optionalUser.get();

        if(!account.getPw().equals(req.getPw())) {
            return null;
        }

        return account;
    }

}
