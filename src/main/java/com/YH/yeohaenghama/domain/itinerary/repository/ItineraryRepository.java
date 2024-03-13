package com.YH.yeohaenghama.domain.itinerary.repository;

import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItineraryRepository extends JpaRepository<Itinerary, Long> {

    Optional<Itinerary> findByIdAndAccountId(Long itineraryId,Long accountId);
    List<ItineraryProjection> findByAccountId(Long accountId);

    interface ItineraryProjection {
        Long getId();
        String getName();
        String getStartDate();
        String getEndDate();
    }
}
