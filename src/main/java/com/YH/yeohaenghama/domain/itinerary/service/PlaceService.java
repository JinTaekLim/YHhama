package com.YH.yeohaenghama.domain.itinerary.service;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.itinerary.dto.PlaceJoinDTO;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryRepository;
import com.YH.yeohaenghama.domain.itinerary.repository.PlaceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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
            throw new RuntimeException("해당 id 값을 가진 일정이 존재하지 않습니다. : " + itineraryId);
        }


        log.info("조회 성공");



        List<Place> placesToDelete = placeRepository.findByItineraryId(itineraryId);
        if (!placesToDelete.isEmpty()) {
            placeRepository.deleteAll(placesToDelete);
            log.info("해당 id 값을 가진 일정에 속한 장소들이 모두 삭제되었습니다.");
        }


        for (PlaceJoinDTO placeDTO : placeDTOs) {
            Place place = new Place();
            place.setDay(placeDTO.getDay());
            place.setPlaceName(placeDTO.getPlaceName());
            place.setPlaceNum(placeDTO.getPlaceNum());
            place.setItinerary(itinerary);
            placeRepository.save(place);
        }
        log.info("새로운 장소 추가 성공");
    }

}
