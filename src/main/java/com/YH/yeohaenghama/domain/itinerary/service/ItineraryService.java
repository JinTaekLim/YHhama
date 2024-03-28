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
import com.YH.yeohaenghama.domain.openApi.dto.OpenApiDirectionsDTO;
import com.YH.yeohaenghama.domain.openApi.dto.OpenApiGetXY;
import com.YH.yeohaenghama.domain.openApi.service.OpenApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Slf4j
@RequiredArgsConstructor

public class ItineraryService {
    private final ItineraryRepository itineraryRepository;
    private final AccountRepository accountRepository;
    private final DiaryRepository diaryRepository;
    private final DiaryService diaryService;
    private final OpenApiService openApiService;

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


            LocalDate startDate = itinerary.getStartDate();
            LocalDate endDate = itinerary.getEndDate();

            long numOfDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;

            System.out.println("기간은 " + numOfDays + "일입니다.");


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
            if(places.isEmpty()){
                log.info("djqts?");
                for (int i = 1; i <= numOfDays; i++) {
                    String dayKey = "Day-" + i;
                    placesByDay.put(dayKey, new ArrayList<>());
                }
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



    public ItineraryShowMain.Response showMain(ItineraryShowMain.Request dto) throws IOException {
        log.info(String.valueOf(dto));

        Optional<Itinerary> itineraryOpt = itineraryRepository.findByIdAndAccountId(dto.getItineraryId(), dto.getAccountId());

        if(itineraryOpt.isEmpty()){
            throw new NoSuchElementException("유저가 해당 ID를 가진 일정을 가지고 있지 않습니다. ");
        }
        Itinerary itinerary = itineraryOpt.get();

        ItineraryShowMain.Response response = new ItineraryShowMain.Response();
        response.setItineraryId(itinerary.getId());


        LocalDate currentDate = LocalDate.now();
        LocalDate savedDate = itinerary.getStartDate(); // 이미 LocalDate 값을 가지고 있다고 가정합니다.
        long daysDifference = ChronoUnit.DAYS.between(currentDate,savedDate);


        response.setDDay(daysDifference);   // D-day



        List<Place> places = placeRepository.findByItineraryId(itinerary.getId());
        if(places.isEmpty()){
            throw new NoSuchElementException("해당 일정에는 장소가 저장되어있지 않습니다. ");
        }




        Map<String, Map<String, Object>> placesMap = new HashMap<>();
        Map<String, Map<String, Object>> result = new HashMap<>();

        for (Place place : places) {
            String getDay = String.valueOf(place.getDay());

            Map<String, Object> placeMap = new LinkedHashMap<>();
            placeMap.put("name", place.getPlaceName());
            placeMap.put("place_num", place.getPlaceNum());
            placeMap.put("place_type", place.getPlaceType());
            placeMap.put("startTime", place.getStartTime());
            placeMap.put("endTime", place.getEndTime());


            if (!placesMap.containsKey(getDay)) {
                placesMap.put(getDay, new HashMap<>());
                placesMap.get(getDay).put("places", new ArrayList<>());
                placesMap.get(getDay).put("trans", new ArrayList<>());
            }

            ((List<Map<String, Object>>) placesMap.get(getDay).get("places")).add(placeMap);
        }

        for (String day : placesMap.keySet()) {
            List<Map<String, Object>> dayPlaces = (List<Map<String, Object>>) placesMap.get(day).get("places");
            List<Map<String, Object>> trans = (List<Map<String, Object>>) placesMap.get(day).get("trans");

            for (int i = 0; i < dayPlaces.size(); i += 2) {
                Map<String, Object> place1 = dayPlaces.get(i);
                Map<String, Object> place2 = (i + 1 < dayPlaces.size()) ? dayPlaces.get(i + 1) : null;

                Map<String, Object> tran = new HashMap<>();
                tran.put("startPlace", place1.get("name"));
                tran.put("endPlace", (place2 != null) ? place2.get("name") : "");


                if(place2 != null) {
                    OpenApiGetXY.Response openApiGetXY = openApiService.getXY((String) place1.get("place_num"), (String) place1.get("place_type"));  // 출발 장소 좌표

                    OpenApiGetXY.Response openApiGetXY2 = openApiService.getXY((String) place2.get("place_num"), (String) place2.get("place_type"));  // 도착 장소 좌표

//                    log.info("x = " + openApiGetXY.getX() + " y = " + openApiGetXY.getY());
//                    log.info("2x = " + openApiGetXY2.getX() + " 2y = " + openApiGetXY2.getY());

                    if (openApiGetXY2 != null) {
                        OpenApiDirectionsDTO openApiDirectionsDTO = new OpenApiDirectionsDTO();
                        openApiDirectionsDTO.setSx(openApiGetXY.getX());
                        openApiDirectionsDTO.setSy(openApiGetXY.getY());
                        openApiDirectionsDTO.setEx(openApiGetXY2.getX());
                        openApiDirectionsDTO.setEy(openApiGetXY2.getY());

                        tran.put("time", openApiService.getDirectionsTransport(openApiDirectionsDTO));
                    }
                    trans.add(tran);
                }
            }

            Map<String, Object> dayResult = new HashMap<>();
            dayResult.put("places", dayPlaces);
            dayResult.put("trans", trans);

            result.put(day, dayResult);
        }




        response.setPlace(placesMap);




        return response;
    }
}




