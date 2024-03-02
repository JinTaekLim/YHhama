package com.YH.yeohaenghama.domain.diary.entity;

import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Diary {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "itinerary_id", referencedColumnName = "id")
    private Long itinerary;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ElementCollection
    @CollectionTable(name = "diary_photo_url", joinColumns = @JoinColumn(name = "diary_id"))
    @Column(name = "photo_url", nullable = false)
    private List<String> photoURL;

    @Builder
    public Diary(String date, String title, String content, List<String> photoURL, Long itinerary) {
        this.date = date;
        this.title = title;
        this.content = content;
        this.photoURL = photoURL;
        this.itinerary = itinerary;
    }

}
