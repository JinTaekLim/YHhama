package com.YH.yeohaenghama.domain.shorts.entity;


import com.YH.yeohaenghama.domain.account.entity.Account;
import jakarta.persistence.*;
import lombok.Getter;

@Entity @Getter
public class ShortsLike {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shorts_id")
    private Shorts shorts;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
