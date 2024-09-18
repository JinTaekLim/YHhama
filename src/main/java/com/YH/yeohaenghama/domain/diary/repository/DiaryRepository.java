package com.YH.yeohaenghama.domain.diary.repository;

import com.YH.yeohaenghama.domain.diary.entity.Diary;
import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryRepository;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface DiaryRepository extends JpaRepository<Diary,Long> {
    Optional<Diary> findByItinerary(Long itineraryId);

//    @Query("SELECT d FROM Diary d WHERE d.title LIKE %:keyWord%")
//    List<Diary> findByTitleContainingLimit(@Param("keyWord") String keyWord, Pageable pageable);

    List<Diary> findByTitleContaining(String keyWord);
    Page<Diary> findByTitleContaining(String keyWord,Pageable pageable);
    Page<Diary> findByContentContaining(String keyWord, Pageable pageable);
}
