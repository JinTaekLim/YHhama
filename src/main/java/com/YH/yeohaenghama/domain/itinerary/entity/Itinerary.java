package com.YH.yeohaenghama.domain.itinerary.entity;

import com.YH.yeohaenghama.domain.account.entity.Account;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account; // 일정을 제작한 유저 번호

    private String transportation = "bus";      // 교통 수단

    @Column(nullable = false)
    private String area;      // 출발지

    @Column(nullable = false)
    private String startDate;

    @Column(nullable = false)
    private String endDate;

    private String expense = "null";



    public void setAccount(Account account) {
        this.account = account;
    }

    @Builder
    public Itinerary(String name, Long id, String transportation, String area, String startDate, String endDate, String expense) {
        this.name = name;
        this.id = id;
        this.transportation = (transportation != null && !transportation.isEmpty()) ? transportation : "1";;
        this.area = area;
        this.startDate = startDate;
        this.endDate = endDate;
        this.expense = expense;
    }



}
