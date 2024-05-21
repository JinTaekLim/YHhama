package com.YH.yeohaenghama.domain.report.repository;

import com.YH.yeohaenghama.domain.account.controller.Account;
import com.YH.yeohaenghama.domain.report.entity.ReportAccount;
import com.YH.yeohaenghama.domain.report.entity.ReportDiary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReportAccountRepository extends JpaRepository<ReportAccount, Long> {

    List<ReportAccount> findByReportAccountId(Long accountId);
    List<ReportAccount> findByAccountIdOrReportAccountId(Long accountId,Long reportAccountId);

}
