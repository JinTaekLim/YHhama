package com.YH.yeohaenghama.domain.itinerary.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Place {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer day;
    private String startTime;
    private String endTime;
    private String placeType;
    private String placeNum;
    private String placeName;
    private String addr1;
    private double mapx;
    private double mapy;
    private String image;
    private String memo = null;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itinerary_id")
    private Itinerary itinerary;


    @Builder
    public Place(Integer day, String startTime, String endTime, String placeType,
        String placeNum, String placeName, String addr1, double mapx, double mapy, String image,
        String memo, Itinerary itinerary) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.placeType = placeType;
        this.placeNum = placeNum;
        this.placeName = placeName;
        this.addr1 = addr1;
        this.mapx = mapx;
        this.mapy = mapy;
        this.image = image;
        this.memo = memo;
        this.itinerary = itinerary;
    }
}
