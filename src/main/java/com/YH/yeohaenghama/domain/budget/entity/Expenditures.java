package com.YH.yeohaenghama.domain.budget.entity;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor
public class Expenditures {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Setter
    private Long id;

    @ManyToOne
    @JoinColumn(name = "budget_id", referencedColumnName = "id")
    private Budget budget;

    @ManyToOne(optional = true)
    @JoinColumn(name = "place_id", referencedColumnName = "id")
    private Place place;


    @Setter
    @OneToMany(mappedBy = "expenditures", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<ExpendituresGroup> expendituresGroups  = new ArrayList<>();

    @Setter
    @OneToMany(mappedBy = "expenditures", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<ExpendituresSharedAccount> expendituresSharedAccounts  = new ArrayList<>();

    private Integer day;

    private String content;

    private String paymentMethod;

    private String category;

    private boolean individual;


//    public void setBudget(Budget budget){
//        this.budget = budget;
//    }
//    public void setPlace(Place place){
//        this.place = place;
//    }
//
//    public void setAccount(Account account){
//        this.account = account;
//    }

    @Builder
    public Expenditures(Budget budget, Place place, List<ExpendituresGroup> expendituresGroups, Integer day, String content, String paymentMethod, String category, boolean individual) {
        this.budget = budget;
        this.place = place;
        this.expendituresGroups = expendituresGroups;
        this.day = day;
        this.content = content;
        this.paymentMethod = paymentMethod;
        this.category = category;
        this.individual = individual;
    }

}
