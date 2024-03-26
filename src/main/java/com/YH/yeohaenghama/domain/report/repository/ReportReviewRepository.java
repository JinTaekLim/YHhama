package com.YH.yeohaenghama.domain.report.repository;

import com.YH.yeohaenghama.domain.report.entity.ReportDiary;
import com.YH.yeohaenghama.domain.report.entity.ReportReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReportReviewRepository extends JpaRepository<ReportReview,Long> {
    Optional<ReportReview> findByAccountIdAndReviewId(Long accountId, Long reviewId);

    List<ReportReview> findByReviewId(Long reviewId);
}
