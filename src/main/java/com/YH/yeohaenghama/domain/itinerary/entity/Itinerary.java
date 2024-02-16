package com.YH.yeohaenghama.domain.itinerary.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Itinerary {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String account;

    private String transportation = "bus";

    @Column(nullable = false)
    private String startPlace;

    @Column(nullable = false)
    private String endPlace;

    @Column(nullable = false)
    private String startDate;

    @Column(nullable = false)
    private String endDate;

    private String expense = "null";


    @Builder
    public Itinerary(String name, Long id, String account, String transportation, String startPlace, String endPlace, String startDate, String endDate, String expense) {
        this.name = name;
        this.id = id;
        this.account = account;
        this.transportation = (transportation != null && !transportation.isEmpty()) ? transportation : "1";;
        this.startPlace = startPlace;
        this.endPlace = endPlace;
        this.startDate = startDate;
        this.endDate = endDate;
        this.expense = expense;
    }



}
