package com.YH.yeohaenghama.domain.rating.repository;

import com.YH.yeohaenghama.domain.account.entity.AccountSavePlace;
import com.YH.yeohaenghama.domain.rating.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByContentTypeIdAndContentId(Long contentTypeId, Long contentId);
}
