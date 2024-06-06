package com.YH.yeohaenghama.domain.budget.entity;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.itinerary.entity.ItineraryJoinAccount;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class ExpendituresGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "expenditures", referencedColumnName = "id")
    private Expenditures expenditures;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    private Integer amount;




    @Builder
    public ExpendituresGroup(Account account,Expenditures expenditures, Integer amount) {
        this.account = account;
        this.expenditures = expenditures;
        this.amount = amount;
    }
}
