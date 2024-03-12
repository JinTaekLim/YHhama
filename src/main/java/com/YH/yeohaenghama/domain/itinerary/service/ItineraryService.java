package com.YH.yeohaenghama.domain.itinerary.service;

import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.account.repository.AccountRepository;
import com.YH.yeohaenghama.domain.diary.entity.Diary;
import com.YH.yeohaenghama.domain.diary.repository.DiaryRepository;
import com.YH.yeohaenghama.domain.diary.service.DiaryService;
import com.YH.yeohaenghama.domain.itinerary.dto.*;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
//import com.YH.yeohaenghama.domain.itinerary.entity.ItineraryType;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryRepository;
//import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryTypeRepository;
import com.YH.yeohaenghama.domain.itinerary.repository.PlaceRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor

public class ItineraryService {
    private final ItineraryRepository itineraryRepository;
    private final AccountRepository accountRepository;
    private final DiaryRepository diaryRepository;
    private final DiaryService diaryService;

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

    public ItineraryJoinDTO.Response update(Long itineraryId, ItineraryJoinDTO.Request reqDTO, Long accountId) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NoSuchElementException("해당 id 값을 가진 유저가 존재하지 않습니다. : " + accountId));

        Itinerary itinerary = itineraryRepository.findById(itineraryId)
                .orElseThrow(() -> new NoSuchElementException("해당 id 값을 가진 일정이 존재하지 않습니다. : " + itineraryId));

        if (!itinerary.getAccount().getId().equals(account.getId())) {
            throw new IllegalArgumentException("해당 계정에 속한 일정이 아닙니다.");
        }

        itinerary.update(reqDTO);

        itinerary = itineraryRepository.save(itinerary);

        log.info("일정 업데이트 완료");

        return ItineraryJoinDTO.Response.fromEntity(itinerary);
    }


    public ItineraryShowDTO getItineraryInfo(Long itineraryId) {
        Optional<Itinerary> optionalItinerary = itineraryRepository.findById(itineraryId);
        if (optionalItinerary.isPresent()) {
            Itinerary itinerary = optionalItinerary.get();
            Account account = itinerary.getAccount();
            AccountShowDTO.Response accountShowDTO = new AccountShowDTO.Response(account.getId(), account.getNickname(),null);

            List<Place> places = itinerary.getPlaces();
            Map<String, List<PlaceShowDTO>> placesByDay = new HashMap<>();

            for (Place place : places) {
                PlaceShowDTO placeShowDTO = new PlaceShowDTO();
                placeShowDTO.setStartTime(place.getStartTime());
                placeShowDTO.setEndTime(place.getEndTime());
                placeShowDTO.setPlaceType(place.getPlaceType());
                placeShowDTO.setPlaceNum(place.getPlaceNum());
                String dayKey = "Day-" + place.getDay();
                placeShowDTO.setPlaceName(place.getPlaceName());
                placeShowDTO.setMemo(place.getMemo());

                placesByDay.computeIfAbsent(dayKey, k -> new ArrayList<>()).add(placeShowDTO);
            }

            return new ItineraryShowDTO(itinerary, accountShowDTO, placesByDay);
        } else {
            throw new NoSuchElementException("해당 id 값을 가진 일정이 존재하지 않습니다. : " + itineraryId);
        }
    }

    public void deleteItinerary(Long itineraryId) throws IOException {
        Optional<Itinerary> optionalItinerary = itineraryRepository.findById(itineraryId);

        if (optionalItinerary.isPresent()) {
            log.info(String.valueOf("deleteItinerary : " + itineraryId));


            Optional<Diary> diaryOptional = diaryRepository.findByItinerary(itineraryId);
            if (diaryOptional.isPresent()) {
                diaryService.delete(itineraryId);
                log.info("일기 삭제 완");
            }

            log.info("일정 삭제 완");
            itineraryRepository.deleteById(itineraryId);
        } else {
            throw new NoSuchElementException("해당 id 값을 가진 일정이 존재하지 않습니다. : " + itineraryId);
        }
    }

    public List<ItineraryRepository.ItineraryProjection> getItinerariesByAccountId(Long accountId) {
        accountRepository.findById(accountId)
                .orElseThrow(() -> new NoSuchElementException("해당 id 값을 가진 유저가 존재하지 않습니다. : " + accountId));
        return itineraryRepository.findByAccountId(accountId);
    }

    public final PlaceRepository placeRepository;

    public int getPlaceLength(Long ItineraryId){
        log.info(String.valueOf(placeRepository.findByItineraryId(ItineraryId).size()));
        return placeRepository.findByItineraryId(ItineraryId).size();
    }
}




