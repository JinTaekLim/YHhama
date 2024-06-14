package com.YH.yeohaenghama.domain.addPlace.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@RequiredArgsConstructor
public class AddPlace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String tel;
    private String add1;
    private String add2;
    private String mapX;
    private String mapY;

    @Builder
    public AddPlace(Long id, String title, String tel, String add1, String add2, String mapX, String mapY, String imageUrl) {
        this.id = id;
        this.title = title;
        this.tel = tel;
        this.add1 = add1;
        this.add2 = add2;
        this.mapX = mapX;
        this.mapY = mapY;
    }
}
