package com.YH.yeohaenghama.domain.budget.entity;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor
public class Expenditures {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "budget_id", referencedColumnName = "id")
    private Budget budget;

    @ManyToOne(optional = true)
    @JoinColumn(name = "place_id", referencedColumnName = "id")
    private Place place;

    private Integer day;

    private String content;

    private String paymentMethod;

    private String category;

    private String name;

    private Integer amount;


    public void setBudget(Budget budget){
        this.budget = budget;
    }
    public void setPlace(Place place){
        this.place = place;
    }

    public void setAccount(Account account){
        this.account = account;
    }

    @Builder
    public Expenditures(Account account, Budget budget, Place place, Integer day, String content, String paymentMethod, String category, String name, Integer amount) {
        this.account = account;
        this.budget = budget;
        this.place = place;
        this.day = day;
        this.content = content;
        this.paymentMethod = paymentMethod;
        this.category = category;
        this.name = name;
        this.amount = amount;
    }

}
