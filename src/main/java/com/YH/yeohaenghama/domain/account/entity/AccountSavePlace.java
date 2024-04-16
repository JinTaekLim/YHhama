package com.YH.yeohaenghama.domain.account.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class AccountSavePlace {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private Long itineraryId;

    @Column(nullable = false)
    private String placeNum;
    @Column(nullable = false)
    private String contentTypeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    public AccountSavePlace(Long itineraryId,String placeNum, String contentTypeId, Account account) {
        this.itineraryId = itineraryId;
        this.placeNum = placeNum;
        this.contentTypeId = contentTypeId;
        this.account = account;
    }
}

