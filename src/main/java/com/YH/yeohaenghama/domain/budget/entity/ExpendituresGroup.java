package com.YH.yeohaenghama.domain.budget.entity;

import com.YH.yeohaenghama.domain.itinerary.entity.ItineraryJoinAccount;
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
    @JoinColumn(name = "budget", referencedColumnName = "id")
    private Budget budget;

    @ManyToOne
    @JoinColumn(name = "accountId",referencedColumnName = "id")
    private ItineraryJoinAccount itineraryJoinAccount;

    @ManyToOne(optional = true)
    @JoinColumn(name = "place_id", referencedColumnName = "id")
    private Place place;

    private Integer day;

    private String paymentMethod;

    private String category;

    private String name;

    private Integer amount;

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public void setItineraryAccount(ItineraryJoinAccount itineraryJoinAccount){ this.itineraryJoinAccount = itineraryJoinAccount; }

    @Builder
    public ExpendituresGroup(Budget budget,ItineraryJoinAccount itineraryJoinAccount, Place place, Integer day, String paymentMethod, String category, String name, Integer amount) {
        this.budget = budget;
        this.itineraryJoinAccount = itineraryJoinAccount;
        this.place = place;
        this.day = day;
        this.paymentMethod = paymentMethod;
        this.category = category;
        this.name = name;
        this.amount = amount;
    }
}
