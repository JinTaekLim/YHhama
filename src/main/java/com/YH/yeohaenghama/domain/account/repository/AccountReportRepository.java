package com.YH.yeohaenghama.domain.account.repository;

import com.YH.yeohaenghama.domain.account.entity.AccountReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountReportRepository extends JpaRepository<AccountReport, Long> {
    List<AccountReport> findByAccountId(Long accountId);
}
