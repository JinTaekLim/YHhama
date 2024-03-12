package com.YH.yeohaenghama.domain.review.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Entity
@Getter
@NoArgsConstructor
public class ReviewPhotoURL {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;


    private String photoUrl;


    public ReviewPhotoURL(Review review, String photoUrl) {
        this.review = review;
        this.photoUrl = photoUrl;
    }

    public void setPhotoUrl(String photoUrl){
        this.photoUrl = photoUrl;
    }
    public void setReview(Review review){
        this.review = review;
    }

}
