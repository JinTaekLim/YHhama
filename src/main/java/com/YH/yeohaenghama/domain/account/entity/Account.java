package com.YH.yeohaenghama.domain.account.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Account {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String pw;
    private String photoUrl;
    private String nickname;

    private AccountRole role = AccountRole.ACCOUNT;
}
