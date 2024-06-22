package com.YH.yeohaenghama.domain.itinerary.repository;

import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    List<Place> findByItineraryId(Long itineraryId);
    List<Place> findByItineraryIdAndDay(Long itineraryId, int day);

    List<Place> findByPlaceNumAndPlaceType(String placeNum,String placeType);

    List<Place> findByItineraryIdAndPlaceNumAndPlaceType(Long itineraryId, String placeNum,String placeType);

    Page<Place> findByPlaceNameContaining(String placeName, Pageable pageable);
}
