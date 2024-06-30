package com.YH.yeohaenghama.domain.shorts.repository;

import com.YH.yeohaenghama.domain.shorts.entity.ShortsLike;
import jakarta.transaction.Transactional;
import org.hibernate.sql.Delete;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShortsLikeRepository extends JpaRepository<ShortsLike,Long> {
    Optional<ShortsLike> findByShortsIdAndAccountId(Long shortsId, Long accountId);

}
