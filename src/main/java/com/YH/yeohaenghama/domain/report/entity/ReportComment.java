package com.YH.yeohaenghama.domain.report.entity;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.diary.entity.Comment;
import com.YH.yeohaenghama.domain.diary.entity.Diary;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ReportComment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Builder
    public ReportComment(Account account, Comment comment) {
        this.account = account;
        this.comment = comment;
    }
}
