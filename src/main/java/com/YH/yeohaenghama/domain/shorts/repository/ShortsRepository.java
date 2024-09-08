package com.YH.yeohaenghama.domain.shorts.repository;

import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.shorts.entity.Shorts;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShortsRepository extends JpaRepository<Shorts,Long> {
    Page<Shorts> findAll(Pageable pageable);
    void deleteByItineraryId(Long itineraryId);

    @Query("SELECT s FROM Shorts s WHERE s.title LIKE %:title%")
    List<Shorts> findByTitle(@Param("title") String title, Pageable pageable);

    @Query("SELECT s FROM Shorts s WHERE s.content LIKE %:title%")
    List<Shorts> findByContent(@Param("title") String title, Pageable pageable);


    List<Shorts> findByItineraryId(Long itineraryId);
}
