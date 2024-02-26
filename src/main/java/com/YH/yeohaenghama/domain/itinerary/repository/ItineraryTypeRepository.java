package com.YH.yeohaenghama.domain.itinerary.repository;

import com.YH.yeohaenghama.domain.itinerary.entity.ItineraryType;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItineraryTypeRepository extends JpaRepository<ItineraryType, Long> {
    List<ItineraryType> findByItineraryId(Long itineraryId);
}
