package com.YH.yeohaenghama.domain.diary.repository;

import com.YH.yeohaenghama.domain.diary.entity.Diary;
import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary,Long> {
    Optional<Diary> findByItinerary(Long itineraryId);
    List<Diary> findByTitle(String keyWord);
    List<Diary> findByContentContaining(String keyWord);
}
