package com.YH.yeohaenghama.domain.itinerary.service;

import com.YH.yeohaenghama.domain.account.dto.AccountLoginDTO;
import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.account.repository.AccountRepository;
import com.YH.yeohaenghama.domain.itinerary.dto.ItineraryJoinDTO;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor

public class ItineraryService {
    private final ItineraryRepository itineraryRepository;

    public ItineraryJoinDTO.Response save(ItineraryJoinDTO.Request reqDTO) {
        ItineraryJoinDTO itineraryJoinDTO = new ItineraryJoinDTO(reqDTO);
        Itinerary itinerary = itineraryRepository.save(itineraryJoinDTO.toEntity());
        return ItineraryJoinDTO.Response.fromEntity(itinerary);
    }



}
