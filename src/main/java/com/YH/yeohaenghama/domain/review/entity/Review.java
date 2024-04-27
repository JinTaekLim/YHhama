package com.YH.yeohaenghama.domain.review.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Review {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String placeName;   // 장소 이름
    private Long contentId;     // 장소 번호
    private Long contentTypeId; // 관광 타입 번호
    private Long rating;    // 평점
    private String content; // 리뷰 내용
    private LocalDateTime date; //일시

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewPhotoURL> reviewPhotoURLS;   // 리뷰 사진 URL

    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Long accountId; // 유저 ID


    public Review(List<ReviewPhotoURL> reviewPhotoURLS) {
        this.reviewPhotoURLS = reviewPhotoURLS;
    }

    @Builder
    public Review(String placeName, Long contentId, Long contentTypeId, Long rating, Long accountId, String content, List<ReviewPhotoURL> reviewPhotoURLS) {
        this.placeName = placeName;
        this.contentId = contentId;
        this.contentTypeId = contentTypeId;
        this.rating = rating;
        this.content = content;
        this.reviewPhotoURLS = reviewPhotoURLS;
        this.accountId = accountId;
        this.date = LocalDateTime.now();
    }


    public void update(String placeName, Long contentId, Long contentTypeId, Long rating, String content, List<ReviewPhotoURL> newReviewPhotoURLS) {
        this.placeName = placeName;
        this.contentId = contentId;
        this.contentTypeId = contentTypeId;
        this.rating = rating;
        this.content = content;
        this.date = LocalDateTime.now();

        for (int i = 0; i < this.reviewPhotoURLS.size(); i++) {
            if (i < newReviewPhotoURLS.size()) {
                this.reviewPhotoURLS.get(i).setPhotoUrl(newReviewPhotoURLS.get(i).getPhotoUrl());
            }
        }

        for (int i = this.reviewPhotoURLS.size(); i < newReviewPhotoURLS.size(); i++) {
            newReviewPhotoURLS.get(i).setReview(this);
            this.reviewPhotoURLS.add(newReviewPhotoURLS.get(i));
        }
    }



}

