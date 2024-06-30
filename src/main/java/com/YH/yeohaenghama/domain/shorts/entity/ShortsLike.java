package com.YH.yeohaenghama.domain.shorts.entity;


import com.YH.yeohaenghama.domain.account.entity.Account;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
@Table(name = "shorts_like", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"shorts_id", "account_id"})
})
@NoArgsConstructor
public class ShortsLike {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shorts_id")
    private Shorts shorts;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Builder
    public ShortsLike(Shorts shorts, Account account) {
        this.shorts = shorts;
        this.account = account;
    }
}
