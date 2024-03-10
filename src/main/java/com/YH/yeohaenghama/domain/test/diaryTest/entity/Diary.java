//package com.YH.yeohaenghama.domain.test.diary.entity;
//
//import jakarta.persistence.*;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Getter
//@NoArgsConstructor
//public class Diary {
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @JoinColumn(name = "itinerary_id", referencedColumnName = "id")
//    private Long itinerary;
//
//    @Column(nullable = false)
//    private String date;
//
//    @Column(nullable = false)
//    private String title;
//
//    @Column(nullable = false)
//    private String content;
//
//    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<DiaryPhotoUrl> diaryPhotoUrls;
//
//    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<DiaryDetail> diaryDetails = new ArrayList<>();
//
//    @Builder
//    public Diary(String date, String title, String content, Long itinerary,List<DiaryPhotoUrl> diaryPhotoUrls) {
//        this.date = date;
//        this.title = title;
//        this.content = content;
//        this.itinerary = itinerary;
//        this.diaryPhotoUrls = diaryPhotoUrls;
//    }
//
//}
