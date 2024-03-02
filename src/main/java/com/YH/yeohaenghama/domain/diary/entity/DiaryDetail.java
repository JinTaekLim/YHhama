package com.YH.yeohaenghama.domain.diary.entity;

import jakarta.persistence.*;
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
    @ElementCollection
    private List<String> DayPhotoURL;

    @ManyToOne
    @JoinColumn(name = "diary_id")
    private Diary diary;
}
