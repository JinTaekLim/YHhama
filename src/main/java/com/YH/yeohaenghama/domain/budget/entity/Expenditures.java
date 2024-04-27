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
    @JoinColumn(name = "budget_id", referencedColumnName = "id")
    private Budget budget;

    @ManyToOne(optional = true)
    @JoinColumn(name = "place_id", referencedColumnName = "id")
    private Place place;

    private Integer day;

    private String paymentMethod;

    private String category;

    private String name;

    private Integer amount;


    public void setBudgetPlace(Budget budget,Place place){
        this.budget = budget;
        this.place = place;
    }

    @Builder
    public Expenditures(Budget budget, Place place, Integer day, String paymentMethod, String category, String name, Integer amount) {
        this.budget = budget;
        this.place = place;
        this.day = day;
        this.paymentMethod = paymentMethod;
        this.category = category;
        this.name = name;
        this.amount = amount;
    }

}
