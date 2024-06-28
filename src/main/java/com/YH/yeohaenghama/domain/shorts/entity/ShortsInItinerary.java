//package com.YH.yeohaenghama.domain.shorts.entity;
//
//import com.YH.yeohaenghama.domain.account.entity.Account;
//import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
//import jakarta.persistence.*;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Getter
//@NoArgsConstructor
//public class ShortsInItinerary {
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "shorts_id")
//    private Shorts shorts;
//
//    @ManyToOne
//    @JoinColumn(name = "itinerary_id")
//    private Itinerary itinerary;
//
//
//
//    @Builder
//    public ShortsInItinerary(Shorts shorts, Itinerary itinerary) {
//        this.shorts = shorts;
//        this.itinerary = itinerary;
//    }
//}
