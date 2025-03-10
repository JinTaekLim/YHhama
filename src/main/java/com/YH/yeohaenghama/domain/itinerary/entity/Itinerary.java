package com.YH.yeohaenghama.domain.itinerary.entity;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.budget.entity.Budget;
import com.YH.yeohaenghama.domain.budget.entity.Expenditures;
import com.YH.yeohaenghama.domain.diary.entity.Diary;
import com.YH.yeohaenghama.domain.itinerary.dto.ItineraryJoinDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
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
    @ElementCollection
    private List<String> type;    // 누구와 여행하는지

    @Column(nullable = false)
    @ElementCollection
    private List<String> itineraryStyle;    // 여행 스타일

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account; // 일정을 제작한 유저 번호

    private String transportation = "bus";      // 교통 수단

    @Column(nullable = false)
    private String area;      // 출발지

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @OneToMany
    @JoinColumn(name = "itinerary_id", referencedColumnName = "id")
    private List<Place> places;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "itinerary_id", referencedColumnName = "id")
    private Diary diary;

    @OneToMany
    @JoinColumn(name = "itinerary_id", referencedColumnName = "id")
    private List<ItineraryJoinAccount> joinAccount;

    public void update(ItineraryJoinDTO.Request reqDTO) {

        this.name = reqDTO.getName();
        this.type = reqDTO.getType();
        this.itineraryStyle = reqDTO.getStyle();
        this.transportation = reqDTO.getTransportation();
        this.area = reqDTO.getArea();
        this.startDate = reqDTO.getStartDate();
        this.endDate = reqDTO.getEndDate();
    }

    public void setPlaceItinerary(List<Place> placeList){
        this.places = placeList;
    }
    public void setAccount(Account account) {
        this.account = account;
    }

    @Builder
    public Itinerary(String name, List<String> type, List<String> itineraryStyle, Long id, String transportation, String area, LocalDate startDate, LocalDate endDate, String expense, List<Place> places) {
        this.name = name;
        this.type = type;
        this.itineraryStyle = itineraryStyle;
        this.id = id;
        this.transportation = (transportation != null && !transportation.isEmpty()) ? transportation : "1";;
        this.area = area;
        this.startDate = startDate;
        this.endDate = endDate;
        this.places = places;
    }



}
