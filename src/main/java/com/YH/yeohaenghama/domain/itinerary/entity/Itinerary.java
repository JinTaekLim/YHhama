package com.YH.yeohaenghama.domain.itinerary.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Itinerary {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;    // 일정 제목

    @Column(nullable = false)
    private String account;     // 일정 제작한 유저 계정

    private String transportation = "bus";      // 교통 수단

    @Column(nullable = false)
    private String area;      // 출발지

    @Column(nullable = false)
    private String startDate;

    @Column(nullable = false)
    private String endDate;

    private String expense = "null";

    @Builder
    public Itinerary(String name, Long id, String account, String transportation, String area, String startDate, String endDate, String expense) {
        this.name = name;
        this.id = id;
        this.account = account;
        this.transportation = (transportation != null && !transportation.isEmpty()) ? transportation : "1";;
        this.area = area;
        this.startDate = startDate;
        this.endDate = endDate;
        this.expense = expense;
    }



}
