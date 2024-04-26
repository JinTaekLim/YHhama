package com.YH.yeohaenghama.domain.itinerary.repository;

import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ItineraryRepository extends JpaRepository<Itinerary, Long> {

    Optional<Itinerary> findByIdAndAccountId(Long itineraryId,Long accountId);
//    List<ItineraryProjection> findByAccountId(Long accountId);
    List<Itinerary> findByAccountId(Long accountId);

    Page<Itinerary> findByArea(String area,Pageable pageable);

    Page<Itinerary> findAll(Pageable pageable);

    interface ItineraryProjection {
        Long getId();
        String getName();
        LocalDate getStartDate();
        LocalDate getEndDate();
    }
}
