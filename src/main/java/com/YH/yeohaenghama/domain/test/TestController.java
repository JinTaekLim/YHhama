//package com.YH.yeohaenghama.domain.test;
//
//import com.YH.yeohaenghama.domain.chatAI.service.ChatAIService2;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.csv.CSVFormat;
//import org.apache.commons.csv.CSVParser;
//import org.apache.commons.csv.CSVRecord;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
//import org.springframework.web.bind.annotation.GetMapping;
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
//  private final ChatAIService2 chatAIService2;
//
//
//
//  @GetMapping("/read")
//  public void test() throws IOException {
//    Resource resource = new ClassPathResource("question.csv");
//    try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
//        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())) {
//
//      for (CSVRecord record : csvParser) {
//        String question = record.get("question").trim();
//        String answer = record.get("answer").trim();
//        String type = record.get("type").trim();
//
//        System.out.println("Question: " + question);
//        System.out.println("Answer: " + answer);
//        System.out.println("Type: " + type);
//        System.out.println();
//
//
//        chatAIService2.insertQuestion(question, answer, type);
//      }
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//  }
//
////
////    private final TestRepository testRepository;
////    @Operation(summary = "저장")
////    @PostMapping("/insert")
////    public void insert(String question, String answer){
////        log.info("insert");
////        testRepository.save(question,answer);
////    }
////
////    @Operation(summary = "읽기")
////    @PostMapping("/read")
////    public Object read(String question){
////        log.info("read");
////        return testRepository.findAnswer(question);
////    }
////
////    @Operation(summary = "전체 읽기")
////    @PostMapping("/readAll")
////    public Map<String, String> readAll(){
////        log.info("readAll");
////        return testRepository.findAll();
////    }
////
////    @Operation(summary = "삭제")
////    @PostMapping("/delete")
////    public void delete(Long accountId){
////
////    }
//}
