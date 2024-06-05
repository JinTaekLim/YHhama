package com.YH.yeohaenghama.domain.account.repository;

import com.YH.yeohaenghama.domain.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    Optional<Account> findByEmail(String email);

    Optional<Account> findByPw(String pw);
    Optional<Account> findByexternalId(String externalId);

    List<Account> findByIdIn(List<Long> accountList); // 올바른 메서드 정의
}
