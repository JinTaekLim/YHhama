package com.YH.yeohaenghama.domain.diary.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "itinerary_id", referencedColumnName = "id")
    private Long itinerary;

    @Column(nullable = false)
    private LocalTime date;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiaryPhotoUrl> diaryPhotoUrls;


    @Builder
    public Diary(String title, String content, Long itinerary, List<DiaryPhotoUrl> diaryPhotoUrls) {
        this.date = LocalTime.now(ZoneId.of("Asia/Seoul"));
        this.title = title;
        this.content = content;
        this.itinerary = itinerary;
        this.diaryPhotoUrls = diaryPhotoUrls;

    }

    public void set(String title, String content, Long itinerary, List<DiaryPhotoUrl> diaryPhotoUrls) {
        this.date = LocalTime.now(ZoneId.of("Asia/Seoul"));
        this.title = title;
        this.content = content;
        this.itinerary = itinerary;
        this.diaryPhotoUrls = diaryPhotoUrls;
    }

    public void update(String title, String content, List<DiaryPhotoUrl> diaryPhotoUrls) {
        this.date = LocalTime.now(ZoneId.of("Asia/Seoul"));
        this.title = title;
        this.content = content;

        this.diaryPhotoUrls.clear();

        for (DiaryPhotoUrl newDiaryPhotoUrl : diaryPhotoUrls) {
            newDiaryPhotoUrl.setDiary(this);
            this.diaryPhotoUrls.add(newDiaryPhotoUrl);
        }
    }



}