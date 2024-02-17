//package com.YH.yeohaenghama.domain.test.controller;
//
//import com.YH.yeohaenghama.domain.test.dto.PocketDTO;
//import com.YH.yeohaenghama.domain.test.entity.Pocket;
//import com.YH.yeohaenghama.domain.test.entity.Stone;
//import com.YH.yeohaenghama.domain.test.service.PocketService;
//import com.YH.yeohaenghama.domain.test.service.StoneService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@Slf4j
//@RequestMapping("/api/test")
//public class TestController {
//    private final PocketService pocketService;
//
//    @PostMapping("/pockets")
//    public ResponseEntity<String> createPocket(@RequestBody PocketDTO pocketDTO) {
//        pocketService.createPocketAndStones(pocketDTO);
//        return ResponseEntity.status(HttpStatus.CREATED).body("Pocket created successfully");
//    }
//
//    private final StoneService stoneService;
//    @PostMapping("/pocket/{pocketId}")
//    public List<Stone> getStonesByPocketId(@RequestBody PocketDTO pocketDTO) {
//        return stoneService.getStonesByPocketId(pocketDTO.getId());
//    }
//}
