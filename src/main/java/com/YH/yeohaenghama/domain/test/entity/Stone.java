//package com.YH.yeohaenghama.domain.test.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@NoArgsConstructor
//public class Stone {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "stone_id")
//    private int id;
//
//    private String stoneName;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "pocket_id")
//    private Pocket pocket;
//
//    @Builder
//    public Stone(String stoneName, Pocket pocket) {
//        this.stoneName = stoneName;
//        this.pocket = pocket;
//    }
//
//    public static Stone createStone(String stoneName, Pocket pocket) {
//        return Stone.builder()
//                .stoneName(stoneName)
//                .pocket(pocket)
//                .build();
//    }
//}