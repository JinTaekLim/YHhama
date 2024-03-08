package com.YH.yeohaenghama.domain.review.repository;

import com.YH.yeohaenghama.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByContentTypeIdAndContentId(Long contentTypeId, Long contentId);
    List<Review> findByContentTypeIdAndContentIdAndAccountId(Long contentTypeId, Long contentId, Long accountId);

}
