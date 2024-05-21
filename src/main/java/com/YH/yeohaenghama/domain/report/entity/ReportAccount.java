package com.YH.yeohaenghama.domain.report.entity;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.diary.entity.Diary;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ReportAccount {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "report_account_id")
    private Account reportAccount;


    public void deleteAccount(){
        this.account = null;
    }
    public void deleteReportAccount(){
        this.reportAccount = null;
    }

    @Builder
    public ReportAccount(Long id, Account account, Account reportAccount) {
        this.id = id;
        this.account = account;
        this.reportAccount = reportAccount;
    }

}
