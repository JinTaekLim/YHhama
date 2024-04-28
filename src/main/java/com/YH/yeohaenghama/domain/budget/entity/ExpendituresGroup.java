package com.YH.yeohaenghama.domain.budget.entity;

import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class ExpendituresGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "budgetAccount", referencedColumnName = "id")
    private BudgetAccount budgetAccount;

    @ManyToOne(optional = true)
    @JoinColumn(name = "place_id", referencedColumnName = "id")
    private Place place;

    private Integer day;

    private String paymentMethod;

    private String category;

    private String name;

    private Integer amount;

    public void setBudgetAccount(BudgetAccount budgetAccount) {
        this.budgetAccount = budgetAccount;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    @Builder
    public ExpendituresGroup(BudgetAccount budgetAccount, Place place, Integer day, String paymentMethod, String category, String name, Integer amount) {
        this.budgetAccount = budgetAccount;
        this.place = place;
        this.day = day;
        this.paymentMethod = paymentMethod;
        this.category = category;
        this.name = name;
        this.amount = amount;
    }
}
