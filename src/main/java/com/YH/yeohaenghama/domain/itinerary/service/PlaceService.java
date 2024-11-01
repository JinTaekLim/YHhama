package com.YH.yeohaenghama.domain.itinerary.service;

import com.YH.yeohaenghama.domain.GCDImage.service.GCSService;
import com.YH.yeohaenghama.domain.itinerary.dto.ItineraryDeletePlaceDTO;
import com.YH.yeohaenghama.domain.itinerary.dto.PlaceCheckInItineraryDTO;
import com.YH.yeohaenghama.domain.itinerary.dto.PlaceJoinDTO;
import com.YH.yeohaenghama.domain.itinerary.dto.PlaceShowDTO;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryRepository;
import com.YH.yeohaenghama.domain.itinerary.repository.PlaceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@Slf4j
@RequiredArgsConstructor

public class PlaceService {

    private final PlaceRepository placeRepository;
    private final ItineraryRepository itineraryRepository;
    private final GCSService gcsService;


    @Transactional
    public void createPlaces(List<PlaceJoinDTO> placeDTOs, Long itineraryId) {
        Itinerary itinerary = itineraryRepository.findById(itineraryId)
            .orElseThrow(() -> new NoSuchElementException("해당 id 값을 가진 일정이 존재하지 않습니다. : " + itineraryId));


        List<Place> placesToDelete = placeRepository.findByItineraryId(itineraryId);
        if (!placesToDelete.isEmpty()) {
            placeRepository.deleteAll(placesToDelete);
            log.info("해당 id 값을 가진 일정에 속한 장소들이 모두 삭제되었습니다.");
        }


        for (PlaceJoinDTO placeDTO : placeDTOs) {
            Place place = dtoToEntity(placeDTO, itinerary);
            placeRepository.save(place);
        }
    }


    public List<PlaceShowDTO> createPlace(PlaceJoinDTO placeDTO, Long itineraryId) {

        Itinerary itinerary = itineraryRepository.findById(itineraryId)
            .orElseThrow(
                () -> new NoSuchElementException("해당 id 값을 가진 일정이 존재하지 않습니다. : " + itineraryId));

        Place place = dtoToEntity(placeDTO, itinerary);
        placeRepository.save(place);

        return PlaceShowDTO.listToDto(itinerary.getPlaces());
    }

        public Place dtoToEntity (PlaceJoinDTO placeDTO, Itinerary itinerary) {
        String photoUrl = "";
        try {
            photoUrl = gcsService.uploadPhoto(
                placeDTO.getImage(),
                String.valueOf(UUID.randomUUID()),
                "place/image/");
        } catch (Exception ignored){
        }

        return Place.builder()
            .day(placeDTO.getDay())
            .startTime(placeDTO.getStartTime())
            .endTime(placeDTO.getEndTime())
            .placeType(placeDTO.getPlaceType())
            .placeName(placeDTO.getPlaceName())
            .addr1(placeDTO.getAddr1())
            .mapx(placeDTO.getMapx())
            .mapy(placeDTO.getMapy())
            .placeNum(placeDTO.getPlaceNum())
            .itinerary(itinerary)
            .image(photoUrl)
            .build();

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

    public List<PlaceShowDTO> show(Long itineraryId) {
        List<Place> places = placeRepository.findByItineraryId(itineraryId);
        if (places.isEmpty()) {
            log.warn("해당 itineraryId를 가진 장소가 존재하지 않습니다. Itinerary ID: {}", itineraryId);
            throw new NoSuchElementException("해당 itineraryId를 가진 장소가 존재하지 않습니다. Itinerary ID : " + itineraryId);
        }

        List<PlaceShowDTO> placeShowDTOList = new ArrayList<>();
        for(Place place : places)
            placeShowDTOList.add(PlaceShowDTO.fromEntity(place));

        return placeShowDTOList;
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

    public Boolean checkItineraryInPlace(PlaceCheckInItineraryDTO dto){
        List<Place> placeList = placeRepository.findByItineraryIdAndPlaceNumAndPlaceType(dto.getItineraryId(),dto.getPlaceNum(),dto.getPlaceType());

        return !placeList.isEmpty();
    }
}
