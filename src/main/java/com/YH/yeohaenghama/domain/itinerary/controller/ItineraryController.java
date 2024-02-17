package com.YH.yeohaenghama.domain.itinerary.controller;

import com.YH.yeohaenghama.domain.itinerary.dto.ItineraryJoinDTO;
import com.YH.yeohaenghama.domain.itinerary.dto.PlaceJoinDTO;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.itinerary.service.ItineraryService;
import com.YH.yeohaenghama.domain.itinerary.service.PlaceService;
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

    @PostMapping("/join")
    public ResponseEntity<ItineraryJoinDTO.Response> createItinerary(@RequestBody ItineraryJoinDTO.Request req){
        log.info("itineraryJoinDTO 확인 ========> {}", req);
        itineraryService.save(req);
        ItineraryJoinDTO.Response response = new ItineraryJoinDTO.Response();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{itineraryId}")
    public ResponseEntity<String> createPlaces(@RequestBody List<PlaceJoinDTO> placeDTOs, @PathVariable Long itineraryId) {
        placeService.createPlaces(placeDTOs, itineraryId);
        return ResponseEntity.status(HttpStatus.CREATED).body("[일정]장소 추가 성공");
    }


}
