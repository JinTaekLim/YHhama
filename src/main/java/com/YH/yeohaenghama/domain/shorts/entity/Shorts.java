package com.YH.yeohaenghama.domain.shorts.entity;


import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Shorts {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String videoUrl;
    private String title;
    private String content;
    private LocalDateTime date;


    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

//    @OneToMany(mappedBy = "shorts", cascade = CascadeType.ALL)
//    private List<ShortsInItinerary> shortsInItinerary = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "itinerary_id")
    private Itinerary itinerary;

    @OneToMany(mappedBy = "shorts", cascade = CascadeType.REMOVE)
    private List<ShortsComment> shortsComments = new ArrayList<>();

    @OneToMany(mappedBy = "shorts", cascade = CascadeType.REMOVE)
    private List<ShortsLike> shortsLikes = new ArrayList<>();




    @Builder
    public Shorts(Long id, String videoUrl, String title, String content, Account account,Itinerary itinerary) {
        this.id = id;
        this.videoUrl = videoUrl;
        this.title = title;
        this.content = content;
        this.account = account;
        this.itinerary = itinerary;
        this.date = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime();
    }
}
