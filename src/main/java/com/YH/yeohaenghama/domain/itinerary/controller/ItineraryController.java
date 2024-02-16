package com.YH.yeohaenghama.domain.itinerary.controller;

import com.YH.yeohaenghama.domain.itinerary.dto.ItineraryJoinDTO;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.itinerary.service.ItineraryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/itinerary")
public class ItineraryController {
    private final ItineraryService itineraryService;


    @PostMapping("/join")
    public ResponseEntity<ItineraryJoinDTO.Response> createItinerary(@RequestBody ItineraryJoinDTO.Request req){
        log.info("itineraryJoinDTO 확인 ========> {}", req);
        itineraryService.save(req);
        ItineraryJoinDTO.Response response = new ItineraryJoinDTO.Response();
        return ResponseEntity.ok(response);
    }


}
