package com.YH.yeohaenghama.domain.shorts.entity;

import com.YH.yeohaenghama.domain.account.entity.Account;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class ShortsComment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shorts_id")
    private Shorts shorts;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    private String comment;

    private LocalDateTime date;


    @Builder
    public ShortsComment(Long id, Shorts shorts, Account account, String comment) {
        this.id = id;
        this.shorts = shorts;
        this.account = account;
        this.comment = comment;
        this.date = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime();
    }
}
