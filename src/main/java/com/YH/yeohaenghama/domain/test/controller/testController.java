//package com.YH.yeohaenghama.domain.test.controller;
//
//
//import com.YH.yeohaenghama.common.apiResult.Response;
//import com.YH.yeohaenghama.domain.test.dto.test2DTO;
//import com.YH.yeohaenghama.domain.test.dto.testDTO;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/test")
//@RequiredArgsConstructor
//@Slf4j
//public class testController {
//    @PostMapping("/posts")
//    public testDTO.Response create(@ModelAttribute testDTO.Request request) {
//        log.info("Received request: {}", request);
//        List<test2DTO.Request> test2DTOList = request.getTest2DTO();
//
//
//        testDTO.Response response = new testDTO.Response();
//        response.setItinerary(request.getItinerary());
//        response.setDate(request.getDate());
//        response.setTitle(request.getTitle());
//        response.setContent(request.getContent());;
////        response.setTest2DTO(request.getTest2DTO());
//
//        log.info(test2DTOList.toString());
//
//
//
//        return response;
//    }
//
//
//
////    @PostMapping("/posts")
////    @ResponseStatus(HttpStatus.CREATED)
////    public void create(@Valid @ModelAttribute testDTO req) {
////        log.info("수신 : " + req.getPhoto());
////    }
//
////
////    @PostMapping("/posts")
//////    @ResponseStatus(HttpStatus.CREATED)
////    public int create(@ModelAttribute testDTO.Request testDTO) {
////        log.info("req : " + testDTO);
////        log.info("req2 : " + testDTO.getTest2DTO());
////        return 200;
////    }
////}
//
//    // 사진 제외 값들 넣는 방법
////    @PostMapping("/posts")
////    public int create(@RequestBody testDTO.Request request) {
////        log.info("Received request: {}", request);
//////        List<test2DTO.Request> test2DTOList = request.getTest2DTO();
////        return 200;
////    }
//
//
//}
