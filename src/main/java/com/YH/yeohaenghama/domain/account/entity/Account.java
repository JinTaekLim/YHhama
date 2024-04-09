package com.YH.yeohaenghama.domain.account.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @Column(unique = true)
    private String externalId = null;

    private AccountRole role = AccountRole.ACCOUNT;

}
