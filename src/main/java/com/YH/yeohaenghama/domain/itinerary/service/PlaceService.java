package com.YH.yeohaenghama.domain.itinerary.service;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.itinerary.dto.ItineraryDeletePlaceDTO;
import com.YH.yeohaenghama.domain.itinerary.dto.PlaceJoinDTO;
import com.YH.yeohaenghama.domain.itinerary.dto.PlaceShowDTO;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryRepository;
import com.YH.yeohaenghama.domain.itinerary.repository.PlaceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor

public class PlaceService {

    private final PlaceRepository placeRepository;
    private final ItineraryRepository itineraryRepository;


    @Transactional
    public void createPlaces(List<PlaceJoinDTO> placeDTOs, Long itineraryId) {
        Itinerary itinerary = itineraryRepository.findById(itineraryId)
                .orElse(null);

        if (itinerary == null) {
            throw new NoSuchElementException("해당 id 값을 가진 일정이 존재하지 않습니다. : " + itineraryId);
        }


        log.info("조회 성공");



        List<Place> placesToDelete = placeRepository.findByItineraryId(itineraryId);
        if (!placesToDelete.isEmpty()) {
            placeRepository.deleteAll(placesToDelete);
            log.info("해당 id 값을 가진 일정에 속한 장소들이 모두 삭제되었습니다.");
        }

        log.info(String.valueOf(placeDTOs));


        for (PlaceJoinDTO placeDTO : placeDTOs) {
            Place place = new Place();
            place.setDay(placeDTO.getDay());
            place.setStartTime(placeDTO.getStartTime());
            place.setEndTime(placeDTO.getEndTime());
            place.setPlaceType(placeDTO.getPlaceType());
            place.setPlaceName(placeDTO.getPlaceName());
            place.setAdd1(placeDTO.getAdd1());
            place.setPlaceNum(placeDTO.getPlaceNum());
            place.setMemo(placeDTO.getMemo());
            place.setItinerary(itinerary);
            placeRepository.save(place);
        }
        log.info("새로운 장소 추가 성공");
    }


    public List<PlaceShowDTO> createPlace(PlaceJoinDTO placeDTO, Long itineraryId) {
        Itinerary itinerary = itineraryRepository.findById(itineraryId)
                .orElse(null);

        if (itinerary == null) {
            throw new NoSuchElementException("해당 id 값을 가진 일정이 존재하지 않습니다. : " + itineraryId);
        }

        Place place = new Place();
        place.setDay(placeDTO.getDay());
        place.setStartTime(placeDTO.getStartTime());
        place.setEndTime(placeDTO.getEndTime());
        place.setPlaceType(placeDTO.getPlaceType());
        place.setPlaceName(placeDTO.getPlaceName());
        place.setPlaceNum(placeDTO.getPlaceNum());
        place.setAdd1(placeDTO.getAdd1());
        place.setMemo(placeDTO.getMemo());
        place.setItinerary(itinerary);

        placeRepository.save(place);


        List<Place> places = show(itineraryId);
        List<PlaceShowDTO> placeDTOs = new ArrayList<>();

        for(Place p : places){
            placeDTOs.add(PlaceShowDTO.fromEntity(p));
        }

        return placeDTOs;


    }

    public List<PlaceShowDTO> deletePlace(ItineraryDeletePlaceDTO.Request dto){

        Itinerary itinerary = itineraryRepository.findById(dto.getItineraryId())
                .orElse(null);

        if (itinerary == null) {
            throw new NoSuchElementException("해당 id 값을 가진 일정이 존재하지 않습니다. : " + dto.getItineraryId());
        }


        Optional<Place> place = placeRepository.findById(dto.getPlaceId());
        if(place.isEmpty()) {
            throw new NoSuchElementException("해당 id 값을 가진 장소가 존재하지 않습니다. : ");
        }

        placeRepository.deleteById(dto.getPlaceId());

        List<Place> placeList = placeRepository.findByItineraryIdAndDay(dto.getItineraryId(), place.get().getDay());

        PlaceShowDTO placeDTO;
        List<PlaceShowDTO> placeShowDTOList = new ArrayList<>();

        for(Place place1 : placeList)
            placeShowDTOList.add(PlaceShowDTO.fromEntity(place1));

        return placeShowDTOList;

    }

    public List<Place> show(Long itineraryId) {
        List<Place> places = placeRepository.findByItineraryId(itineraryId);
        if (places.isEmpty()) {
            log.warn("해당 itineraryId를 가진 장소가 존재하지 않습니다. Itinerary ID: {}", itineraryId);
            throw new NoSuchElementException("해당 itineraryId를 가진 장소가 존재하지 않습니다. Itinerary ID : " + itineraryId);
        }
        return places;
    }

    public List<Long> checkPlace(String placeNum,String placeType){
        List<Place> placeList = placeRepository.findByPlaceNumAndPlaceType(placeNum,placeType);
        if(placeList.isEmpty()) throw new NoSuchElementException("해당 장소를 포함한 일정이 존재하지 않습니다.");

        List<Long> itineraryNum = new ArrayList<>();
        for(Place place : placeList){
            itineraryNum.add(place.getId());
        }


        return itineraryNum;
    }
}
