//package com.YH.yeohaenghama.domain.test;
//
//import io.swagger.v3.oas.annotations.Operation;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Map;
//
//@Slf4j
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/api/test")
//public class TestController {
//
//    private final TestRepository testRepository;
//    @Operation(summary = "저장")
//    @PostMapping("/insert")
//    public void insert(String question, String answer){
//        log.info("insert");
//        testRepository.save(question,answer);
//    }
//
//    @Operation(summary = "읽기")
//    @PostMapping("/read")
//    public Object read(String question){
//        log.info("read");
//        return testRepository.findAnswer(question);
//    }
//
//    @Operation(summary = "전체 읽기")
//    @PostMapping("/readAll")
//    public Map<String, String> readAll(){
//        log.info("readAll");
//        return testRepository.findAll();
//    }
//
//    @Operation(summary = "삭제")
//    @PostMapping("/delete")
//    public void delete(Long accountId){
//
//    }
//}
