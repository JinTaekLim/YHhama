package com.YH.yeohaenghama.domain.review.repository;

import com.YH.yeohaenghama.domain.review.entity.ReviewPhotoURL;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewPhotoURLRepository extends JpaRepository<ReviewPhotoURL, Long> {
    Optional<ReviewPhotoURL> findByReviewId(Long reviewId);
}
