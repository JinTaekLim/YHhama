package com.YH.yeohaenghama.domain.diary.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class DiaryPhotoUrl {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @Column(name = "photo_url")
    private String photoURL;

    @Builder
    public DiaryPhotoUrl(Diary diary, String photoUrl) {
        this.diary = diary;
        this.photoURL = photoUrl;
    }
}
