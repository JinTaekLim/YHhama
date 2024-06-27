package com.YH.yeohaenghama.domain.shorts.repository;

import com.YH.yeohaenghama.domain.shorts.entity.Shorts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortsRepository extends JpaRepository<Shorts,Long> {
}
