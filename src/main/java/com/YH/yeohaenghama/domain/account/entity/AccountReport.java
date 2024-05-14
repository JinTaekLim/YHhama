package com.YH.yeohaenghama.domain.account.entity;

import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class AccountReport {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date warningDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;


    @Builder
    public AccountReport(Long id, Date warningDate, Account account) {
        this.id = id;
        this.warningDate = warningDate;
        this.account = account;
    }
}
