package com.YH.yeohaenghama.domain.itinerary.repository;

import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.Set;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface ItineraryRepository extends JpaRepository<Itinerary, Long> {

    Optional<Itinerary> findByIdAndAccountId(Long itineraryId,Long accountId);
//    List<ItineraryProjection> findByAccountId(Long accountId);
    List<Itinerary> findByAccountId(Long accountId);


    @Query("SELECT i FROM Itinerary i WHERE i.area LIKE %:area%")
    List<Itinerary> findByAreaLimit(@Param("area") String area, Pageable pageable);

    @Query("SELECT i FROM Itinerary i WHERE i.name LIKE %:name%")
    List<Itinerary> findByNameLimit(@Param("name") String name, Pageable pageable);

    Page<Itinerary> findByArea(String area,Pageable pageable);

    Page<Itinerary> findAll(Pageable pageable);

    interface ItineraryProjection {
        Long getId();
        String getName();
        LocalDate getStartDate();
        LocalDate getEndDate();
    }
}
