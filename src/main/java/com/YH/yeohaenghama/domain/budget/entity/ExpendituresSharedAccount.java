package com.YH.yeohaenghama.domain.budget.entity;

import com.YH.yeohaenghama.domain.account.entity.Account;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
public class ExpendituresSharedAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "expenditures", referencedColumnName = "id")
    private Expenditures expenditures;



    @Builder
    public ExpendituresSharedAccount(Account account,Expenditures expenditures) {
        this.account = account;
        this.expenditures = expenditures;
    }

}
