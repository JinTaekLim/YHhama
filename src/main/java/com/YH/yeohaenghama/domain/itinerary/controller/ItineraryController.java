package com.YH.yeohaenghama.domain.itinerary.controller;

import com.YH.yeohaenghama.domain.itinerary.dto.ItineraryJoinDTO;
import com.YH.yeohaenghama.domain.itinerary.dto.ItineraryTypeJoinDTO;
import com.YH.yeohaenghama.domain.itinerary.dto.PlaceJoinDTO;
import com.YH.yeohaenghama.domain.itinerary.dto.PlaceShowDTO;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
//import com.YH.yeohaenghama.domain.itinerary.entity.ItineraryType;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import com.YH.yeohaenghama.domain.itinerary.service.ItineraryService;
import com.YH.yeohaenghama.domain.itinerary.service.PlaceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/itinerary")
public class ItineraryController {
    private final ItineraryService itineraryService;
    private final PlaceService placeService;
//
//    @Operation(summary = "일정 타입 지정")
//    @PostMapping("/join/type/{itineraryId}")
//    public ResponseEntity<ItineraryTypeJoinDTO> saveItineraryType(@RequestBody List<ItineraryTypeJoinDTO> req, @PathVariable Long itineraryId){
//        try {
//            itineraryService.saveType(req, itineraryId);
//            log.info("여행타입 저장 컨트롤");
//            return ResponseEntity.ok().build();
//        } catch (Exception e) {
//            log.error("일정 타입 지정 중 오류 발생", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 오류 응답 반환
//        }
//    }


    @Operation(summary = "일정 생성")
    @PostMapping("/join/{accountId}") //
    public ResponseEntity<ItineraryJoinDTO.Response> saveItinerary(@RequestBody ItineraryJoinDTO.Request reqDTO, @PathVariable Long accountId) {
        try {
            ItineraryJoinDTO.Response response = itineraryService.save(reqDTO, accountId);
            return ResponseEntity.ok(response);
        }
        catch (Exception e){
            return null;
        }
    }

    @Operation(summary = "일정의 장소 추가")
    @PostMapping("/{itineraryId}")
    public ResponseEntity<String> createPlaces(@RequestBody List<PlaceJoinDTO> placeDTOs, @PathVariable Long itineraryId) {
        try {
            placeService.createPlaces(placeDTOs, itineraryId);
            return ResponseEntity.status(HttpStatus.CREATED).body("[일정]장소 추가 성공");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("[일정]장소 추가 실패 : " + e.getMessage());
        }
    }

    @Operation(summary = "제작된 일정 확인")
    @GetMapping("/showItinerary/{itineraryId}")
    public ResponseEntity<Itinerary> showItinerary(@PathVariable Long itineraryId){
        try {
            return ResponseEntity.ok(itineraryService.show(itineraryId));
        }
        catch (Exception e){
            e.getMessage();
            return null;
        }
    }


    @Operation(summary = "제작된 일정 장소 확인")
    @GetMapping("/showPlace/{itineraryId}")
    public ResponseEntity<List<PlaceShowDTO>> showPlace(@PathVariable Long itineraryId) {
        if (itineraryId == null) {
            throw new IllegalArgumentException("itineraryId를 제공해야 합니다.");
        }
        List<Place> places = placeService.show(itineraryId);
        // Place 엔티티를 PlaceDTO로 변환
        List<PlaceShowDTO> placeDTOs = places.stream()
                .map(place -> {
                    PlaceShowDTO dto = new PlaceShowDTO();
                    dto.setDay(place.getDay());
                    dto.setPlaceNum(place.getPlaceNum());
                    dto.setPlaceName(place.getPlaceName());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(placeDTOs);
    }
//
//    @Operation(summary = "일정 정보 상세 보기")
//    @GetMapping("/showItineraryDeteil")
//    public ResponseEntity<Objects[]> ShowItineraryDeteil(@PathVariable Long itineraryId){
//        try{
//
//        } catch (Exception e){
//
//        }
//        return null;
//    }

//
//    @GetMapping("/itinerary/{itineraryId}/withItineraryTypes")
//    public ResponseEntity<List<Itinerary>> getItineraryWithItineraryTypes(@PathVariable Long itineraryId) {
//        List<Itinerary> result = itineraryService.getItineraryWithItineraryTypes(itineraryId);
//        if (!result.isEmpty()) {
//            return ResponseEntity.ok(result);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//    }

}
