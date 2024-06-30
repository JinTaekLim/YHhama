package com.YH.yeohaenghama.domain.shorts.repository;

import com.YH.yeohaenghama.domain.shorts.entity.ShortsComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShortsCommentRepository extends JpaRepository<ShortsComment,Long> {
    List<ShortsComment> findByShortsId(Long shortsId);
}
