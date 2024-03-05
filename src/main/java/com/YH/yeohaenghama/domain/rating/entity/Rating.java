package com.YH.yeohaenghama.domain.rating.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Rating {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long contentId;     // 장소 번호
    private Long contentTypeId; // 관광 타입 번호
    private Long rating;    // 평점

    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Long accountId; // 유저 ID


    @Builder
    public Rating(Long contentId, Long contentTypeId, Long rating, Long accountId) {
        this.contentId = contentId;
        this.contentTypeId = contentTypeId;
        this.rating = rating;
        this.accountId = accountId;
    }
}

