package com.YH.yeohaenghama.domain.report.entity;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.review.entity.Review;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ReportReview {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "itinerary_id")
    private Review review;

    @Builder
    public ReportReview(Account account, Review review) {
        this.account = account;
        this.review = review;
    }
}
