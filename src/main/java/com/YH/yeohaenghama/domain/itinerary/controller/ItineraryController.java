package com.YH.yeohaenghama.domain.itinerary.controller;

import com.YH.yeohaenghama.domain.itinerary.dto.ItineraryJoinDTO;
import com.YH.yeohaenghama.domain.itinerary.dto.PlaceJoinDTO;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import com.YH.yeohaenghama.domain.itinerary.service.ItineraryService;
import com.YH.yeohaenghama.domain.itinerary.service.PlaceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        ItineraryJoinDTO.Response response = itineraryService.save(reqDTO, accountId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "일정의 장소 추가")
    @PostMapping("/{itineraryId}")
    public ResponseEntity<String> createPlaces(@RequestBody List<PlaceJoinDTO> placeDTOs, @PathVariable Long itineraryId) {
        placeService.createPlaces(placeDTOs, itineraryId);
        return ResponseEntity.status(HttpStatus.CREATED).body("[일정]장소 추가 성공");
    }

    @Operation(summary = "제작된 일정 확인")
    @GetMapping("/showItinerary/{itineraryId}")
    public ResponseEntity<Itinerary> showItinerary(@PathVariable Long itineraryId){
        return ResponseEntity.ok(itineraryService.show(itineraryId));

    }


    @Operation(summary = "제작된 일정 장소 확인")
    @GetMapping("/showPlace /{placeId}")
    public ResponseEntity<Place> showPlace(@PathVariable Long placeId){
        return ResponseEntity.ok(placeService.show(placeId));

    }
}
