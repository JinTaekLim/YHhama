package com.YH.yeohaenghama.domain.budget.entity;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Budget {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "itinerary_id", referencedColumnName = "id")
    private Itinerary itinerary;

//    @ManyToOne
//    @JoinColumn(name = "account_id", referencedColumnName = "id")
//    private Account account;

    @OneToMany(mappedBy = "budget", cascade = CascadeType.REMOVE)
    private List<Expenditures> expenditures = new ArrayList<>();

    @OneToMany(mappedBy = "budget", cascade = CascadeType.REMOVE)
    private List<BudgetAccount> budgetAccounts = new ArrayList<>();

    private Integer totalAmount;

    @Builder
    public Budget(Itinerary itinerary, Integer totalAmount) {
        this.itinerary = itinerary;
//        this.account = account;
        this.totalAmount = totalAmount;
    }
}
