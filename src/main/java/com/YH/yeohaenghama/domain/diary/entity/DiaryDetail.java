package com.YH.yeohaenghama.domain.diary.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class DiaryDetail {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String day;
    private String content;

    @OneToMany(mappedBy = "diaryDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiaryDetailPhotoURL> diaryDetailPhotoURLS;

    @ManyToOne
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @Builder
    public DiaryDetail(String day, String content, List<DiaryDetailPhotoURL> diaryDetailPhotoURLS, Diary diary) {
        this.day = day;
        this.content = content;
        this.diaryDetailPhotoURLS = diaryDetailPhotoURLS;
        this.diary = diary;
    }
}
