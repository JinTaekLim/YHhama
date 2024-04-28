package com.YH.yeohaenghama.domain.itinerary.entity;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.budget.entity.Budget;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"itinerary_id" ,"account_id"})
})
public class ItineraryJoinAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "itineraryId", referencedColumnName = "id")
    private Itinerary itinerary;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @Builder
    public ItineraryJoinAccount(Itinerary itinerary, Account account) {
        this.itinerary = itinerary;
        this.account = account;
    }
}


//@Getter
//@Entity
//@NoArgsConstructor
//@Table(uniqueConstraints = {
//        @UniqueConstraint(columnNames = {"account_id", "budget_id"})
//})
//public class BudgetAccount {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "account_id", referencedColumnName = "id")
//    private Account account;
//
//    @ManyToOne
//    @JoinColumn(name = "budget_id", referencedColumnName = "id")
//    private Budget budget;
//
//    @Builder
//    public BudgetAccount(Account account, Budget budget) {
//        this.account = account;
//        this.budget = budget;
//    }
//}