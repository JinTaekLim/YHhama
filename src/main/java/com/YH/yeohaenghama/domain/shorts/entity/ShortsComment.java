package com.YH.yeohaenghama.domain.shorts.entity;

import com.YH.yeohaenghama.domain.account.entity.Account;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Entity
@Getter
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

    private Date date;
}
