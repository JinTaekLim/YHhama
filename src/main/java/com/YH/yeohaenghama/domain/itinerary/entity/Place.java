package com.YH.yeohaenghama.domain.itinerary.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Place {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int day;
    private String placeNum;
    private String placeName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itinerary_id")
    private Itinerary itinerary;
}
