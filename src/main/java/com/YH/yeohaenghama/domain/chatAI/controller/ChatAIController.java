package com.YH.yeohaenghama.domain.chatAI.controller;

import com.YH.yeohaenghama.domain.chatAI.dto.ChatAIDTO;
import com.YH.yeohaenghama.domain.chatAI.service.ChatAIInfo;
import com.YH.yeohaenghama.domain.chatAI.service.ChatAIService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/chatAI")
@RequiredArgsConstructor
@Slf4j
public class ChatAIController {

    private final ChatAIService chatAIService;
    private final ChatAIInfo chatAIInfo;


    @PostMapping("/ask")
    public ChatAIDTO.Response askQuestion(String question) {
        return chatAIService.ask(question);
    }


    @PostMapping("/insert")
    public String insert(ChatAIDTO.insertRequest req) {
        log.info("req = " + req);
        return chatAIService.insertQuestion(req);
    }

//    @PostMapping("/update")
//    public String update(ChatAIDTO.updateRequest req) throws Exception {
//        log.info("req = " + req);
//        return chatAIService.updateQuestion(req);
//    }



    @Operation(summary = "유사 질문 등록")
    @PostMapping("/similarityInsert")
    public Map<String, String> similarityInsert(String question, String question2) throws Exception {
        return chatAIService.similartiyInsert(question,question2);
    }

    @Operation(summary = "조회")
    @PostMapping("/read")
    public Object read(String question){
        return chatAIService.read(question);
    }

    @Operation(summary = "답변 미등록 질문 조회")
    @PostMapping("/getUnansweredQuestions")
    public Map<String,Map<String,String>> getUnansweredQuestions(){return chatAIService.getUnansweredQuestions();}

    @Operation(summary = "Fail 질문 조회")
    @PostMapping("/getFailQuestions")
    public Map<String,Map<String,String>> getFailQuestions(){
        return chatAIService.getFailQuestions();
    }
    @Operation(summary = "전체 조회")
    @PostMapping("/readAll")
    public Map<String, Map<String, String>> readAll(){
        return chatAIService.readAll();
    }

    @Operation(summary = "유사 질문 전체 조회")
    @PostMapping("/similarityReadAll")
    public Map<String, Map<String, String>> similarityReadAll(){
        return chatAIService.readSimilartiyAll();
    }

    @Operation(summary = "질문 삭제")
    @PostMapping("/delete")
    public void delete(String question) {
        chatAIService.delete(question);
    }

    @Operation(summary = "답변 삭제")
    @PostMapping("/deleteAnswer")
    public void deleteAnswer(String question,String answer) {
        chatAIService.deleteAnsewr(question,answer);
    }


    @PostMapping("/test")
    public String test(String test){
        return chatAIInfo.validateArea(test);
    }
}
