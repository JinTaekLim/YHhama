package com.YH.yeohaenghama.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity

public class Account {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Getter @Setter
    private Long accountNum;
    @Getter @Setter
    private String email;
    @Getter @Setter
    private String pw;
    @Getter @Setter
    private String photoUrl;
    @Getter @Setter
    private String nickname;
}
