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
public class Report {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long typeId;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @Builder
    public Report(Long typeId,Account account, Diary diary) {
        this.typeId = typeId;
        this.account = account;
        this.diary = diary;
    }
}
