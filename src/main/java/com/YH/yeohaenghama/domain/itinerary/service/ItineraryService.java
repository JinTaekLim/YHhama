package com.YH.yeohaenghama.domain.itinerary.service;

import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.account.repository.AccountRepository;
import com.YH.yeohaenghama.domain.itinerary.dto.*;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
//import com.YH.yeohaenghama.domain.itinerary.entity.ItineraryType;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryRepository;
//import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryTypeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor

public class ItineraryService {
    private final ItineraryRepository itineraryRepository;
    private final AccountRepository accountRepository;

    public ItineraryJoinDTO.Response save(ItineraryJoinDTO.Request reqDTO, Long accountId) {


        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NoSuchElementException("해당 id 값을 가진 유저가 존재하지 않습니다. : " + accountId));


        ItineraryJoinDTO itineraryJoinDTO = new ItineraryJoinDTO(reqDTO);
        Itinerary itinerary = itineraryJoinDTO.toEntity();


        itinerary.setAccount(account);
        itinerary = itineraryRepository.save(itinerary);

        log.info("일정 저장 완료");

        return ItineraryJoinDTO.Response.fromEntity(itinerary);

    }

    public Itinerary show(Long itineraryId){
        Optional<Itinerary> optionalItinerary = itineraryRepository.findById(itineraryId);

        if (optionalItinerary.isPresent()) {
            return optionalItinerary.get();
        } else {
            throw new NoSuchElementException("해당 id 값을 가진 일정이 존재하지 않습니다. : " + itineraryId);
        }
    }

    public ItineraryShowDTO getItineraryInfo(Long itineraryId) {
        Optional<Itinerary> optionalItinerary = itineraryRepository.findById(itineraryId);
        if (optionalItinerary.isPresent()) {
            Itinerary itinerary = optionalItinerary.get();
            Account account = itinerary.getAccount();
            AccountShowDTO accountShowDTO = new AccountShowDTO(account.getId(), account.getNickname());

            List<Place> places = itinerary.getPlaces();
            List<PlaceShowDTO> placeShowDTOs = new ArrayList<>();
            for (Place place : places) {
                PlaceShowDTO placeShowDTO = new PlaceShowDTO();
                placeShowDTO.setPlaceNum(place.getPlaceNum());
                placeShowDTO.setDay(place.getDay());
                placeShowDTO.setPlaceName(place.getPlaceName());
                placeShowDTOs.add(placeShowDTO);
            }

            return new ItineraryShowDTO(itinerary, accountShowDTO, placeShowDTOs);
        } else {
            throw new NoSuchElementException("해당 id 값을 가진 일정이 존재하지 않습니다. : " + itineraryId);
        }
    }









}

