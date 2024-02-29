package com.YH.yeohaenghama.domain.itinerary.repository;

import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItineraryRepository extends JpaRepository<Itinerary, Long> {

    List<ItineraryProjection> findByAccountId(Long accountId);

    interface ItineraryProjection {
        String getName();
        String getStartDate();
        String getEndDate();
    }
}
