package com.YH.yeohaenghama.domain.itinerary.repository;

import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    List<Place> findByItineraryId(Long itineraryId);
    List<Place> findByItineraryIdAndDay(Long itineraryId, int day);

    List<Place> findByPlaceNumAndPlaceType(String placeNum,String placeType);

    List<Place> findByItineraryIdAndPlaceNumAndPlaceType(Long itineraryId, String placeNum,String placeType);

    List<Place> findByPlaceNameContaining(String placeName);
    @Query("SELECT p FROM Place p WHERE p.placeName LIKE %:placeName%")
    List<Place> findByPlaceNameContainingLimit(@Param("placeName") String placeName, Pageable pageable);

    Page<Place> findByPlaceNameContaining(String placeName, Pageable pageable);
}
