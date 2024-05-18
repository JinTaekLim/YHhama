package com.YH.yeohaenghama.domain.itinerary.repository;

import com.YH.yeohaenghama.domain.itinerary.entity.ItineraryJoinAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItineraryJoinAccountRepository extends JpaRepository<ItineraryJoinAccount, Long> {
    Optional<ItineraryJoinAccount> findByItineraryIdAndAccountId(Long itineraryId, Long accountId);
    List<ItineraryJoinAccount> findByItineraryId(Long itineraryId);
    List<ItineraryJoinAccount> findByAccountId(Long accountId);
}
