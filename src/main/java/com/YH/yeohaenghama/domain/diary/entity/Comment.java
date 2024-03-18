package com.YH.yeohaenghama.domain.diary.entity;

import com.YH.yeohaenghama.domain.account.entity.Account;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;    // 댓글 작성하는 유저 ID

    @ManyToOne
    @JoinColumn(name = "diary_id")
    private Diary diary;        // 댓글 작성하는 일기 ID

    private String content;     // 댓글 내용

    private LocalDateTime date; // 댓글 작성한 일시

    @Builder
    public Comment(Account account, Diary diary, String content, LocalDateTime date) {
        this.account = account;
        this.diary = diary;
        this.content = content;
        this.date = date;
    }
}
