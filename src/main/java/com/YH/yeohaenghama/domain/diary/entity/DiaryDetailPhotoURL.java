package com.YH.yeohaenghama.domain.diary.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class DiaryDetailPhotoURL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "diaryDetail_id")
    private DiaryDetail diaryDetail;

    @Column(name = "photo_url")
    private String photoURL;

    @Builder
    public DiaryDetailPhotoURL(DiaryDetail diaryDetail, String photoUrl) {
        this.diaryDetail = diaryDetail;
        this.photoURL = photoUrl;
    }
}
