//package com.YH.yeohaenghama.domain.test.entity;
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.util.ArrayList;
//import java.util.List;
//@Entity
//@NoArgsConstructor
//public class Pocket {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "pocket_id")
//    private int id;
//
//    private String pocketName;
//    private String pocketColor;
//
//    @OneToMany(mappedBy = "pocket",
//            cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Stone> stones = new ArrayList<>();
//
//    @Builder
//    public Pocket(String pocketName, String pocketColor) {
//        this.pocketName = pocketName;
//        this.pocketColor = pocketColor;
//    }
//
//    public static Pocket createPocket(String pocketName, String pocketColor) {
//        return Pocket.builder()
//                .pocketName(pocketName)
//                .pocketColor(pocketColor)
//                .build();
//    }
//
//    public void putStone(Stone stone) {
//        this.stones.add(stone);
//    }
//}