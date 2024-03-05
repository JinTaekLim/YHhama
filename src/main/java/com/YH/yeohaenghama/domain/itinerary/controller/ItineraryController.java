package com.YH.yeohaenghama.domain.itinerary.controller;

import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.itinerary.dto.*;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
//import com.YH.yeohaenghama.domain.itinerary.entity.ItineraryType;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryRepository;
import com.YH.yeohaenghama.domain.itinerary.service.ItineraryService;
import com.YH.yeohaenghama.domain.itinerary.service.PlaceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/itinerary")
public class ItineraryController {
    private final ItineraryService itineraryService;
    private final PlaceService placeService;

    @Operation(summary = "일정 생성")
    @PostMapping("/join/{accountId}") //
    public ResponseEntity<ItineraryJoinDTO.Response> saveItinerary(@RequestBody ItineraryJoinDTO.Request reqDTO, @PathVariable Long accountId) {
        try {
            ItineraryJoinDTO.Response response = itineraryService.save(reqDTO, accountId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return null;
        }
    }

    @Operation(summary = "일정의 장소 추가")
    @PostMapping("/{itineraryId}")
    public ResponseEntity<String> createPlaces(@RequestBody List<PlaceJoinDTO> placeDTOs, @PathVariable Long itineraryId) {
        try {
            placeService.createPlaces(placeDTOs, itineraryId);
            return ResponseEntity.status(HttpStatus.CREATED).body("[일정]장소 추가 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("[일정]장소 추가 실패 : " + e.getMessage());
        }
    }
//
//    @Operation(summary = "제작된 일정 확인")
//    @GetMapping("/showItinerary/{itineraryId}")
//    public ResponseEntity<Itinerary> showItinerary(@PathVariable Long itineraryId){
//        try {
//            return ResponseEntity.ok(itineraryService.show(itineraryId));
//        }
//        catch (Exception e){
//            e.getMessage();
//            return null;
//        }
//    }


    @Operation(summary = "제작된 일정 장소 확인")
    @GetMapping("/showPlace/{itineraryId}")
    public ResponseEntity<List<PlaceShowDTO>> showPlace(@PathVariable Long itineraryId) {
        if (itineraryId == null) {
            throw new IllegalArgumentException("itineraryId를 입력해주세요.");
        }
        List<Place> places = placeService.show(itineraryId);
        // Place 엔티티를 PlaceDTO로 변환
        List<PlaceShowDTO> placeDTOs = places.stream()
                .map(place -> {
                    PlaceShowDTO dto = new PlaceShowDTO();
                    //dto.setDay(place.getDay());
                    dto.setPlaceNum(place.getPlaceNum());
                    dto.setPlaceName(place.getPlaceName());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(placeDTOs);
    }

    @Operation(summary = "제작된 일정 확인")
    @GetMapping("/{itineraryId}")
    public ResponseEntity<ItineraryShowDTO> getItineraryInfo(@PathVariable Long itineraryId) {
        try {
            ItineraryShowDTO itineraryInfo = itineraryService.getItineraryInfo(itineraryId);
            return ResponseEntity.ok(itineraryInfo);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "유저의 모든 일정 조회")
    @GetMapping("/itineraryShow/{accountId}")
    public ResponseEntity<List<Map<String, Object>>> getItinerariesByAccountId(@PathVariable Long accountId) {
        List<ItineraryRepository.ItineraryProjection> itineraries = itineraryService.getItinerariesByAccountId(accountId);

        List<Map<String, Object>> itineraryData = itineraries.stream()
                .map(itineraryProjection -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("itineraryId", itineraryProjection.getId());
                    data.put("name", itineraryProjection.getName());
                    data.put("startDate", itineraryProjection.getStartDate());
                    data.put("endDate", itineraryProjection.getEndDate());
                    data.put("placeLength", itineraryService.getPlaceLength(itineraryProjection.getId()));
                    return data;
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(itineraryData, HttpStatus.OK);
    }

    @Operation(summary = "일정 삭제")
    @GetMapping("/itinerary/delete/{itineraryId}")
    public ResponseEntity itineraryDelete(@RequestParam Long itineraryId) {
        try {
            itineraryService.deleteItinerary(itineraryId);
            return ResponseEntity.ok("일정 삭제 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("[일정]장소 삭제 실패 : " + e.getMessage());
        }
    }


    @Operation(summary = "일정 수정")
    @PostMapping("/itinerary/update")
    public ResponseEntity itineraryUpdate(@RequestBody ItineraryJoinDTO.Request req, @RequestParam Long accountId, @RequestParam Long itineraryId) {
        try {
            ItineraryJoinDTO.Response response = itineraryService.update(itineraryId, req, accountId);
            return ResponseEntity.ok("[일정]수정 성공\n" + response);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("[일정]수정 실패: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("[일정]수정 실패: " + e.getMessage());
        }
    }



}