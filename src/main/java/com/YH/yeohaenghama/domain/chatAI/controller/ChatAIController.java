package com.YH.yeohaenghama.domain.chatAI.controller;

import com.YH.yeohaenghama.domain.chatAI.dto.ChatAIDTO;
import com.YH.yeohaenghama.domain.chatAI.dto.ChatAIQuestionDTO;
import com.YH.yeohaenghama.domain.chatAI.dto.ChatAnswerDTO;
import com.YH.yeohaenghama.domain.chatAI.entity.ChatAnswer;
import com.YH.yeohaenghama.domain.chatAI.entity.ChatType;
import com.YH.yeohaenghama.domain.chatAI.service.ChatAIService2;
import com.YH.yeohaenghama.domain.chatAI.service.ChatAnswerService;
import com.YH.yeohaenghama.domain.chatAI.service.ChatTypeService;
import com.YH.yeohaenghama.domain.openApi.service.OpenApiService;
import com.google.api.client.util.Value;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi.ChatCompletionRequest;
import org.springframework.data.redis.connection.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/chatAI")
@RequiredArgsConstructor
@Slf4j
public class ChatAIController {

    private final ChatAIService2 chatAIService;
    private final ChatTypeService chatTypeService;
    private final ChatAnswerService chatAnswerService;
//    private final ChatAIInfo chatAIInfo;

    @PostMapping("/ask")
    public ChatAIDTO.Response askQuestion(String question){
        return chatAIService.ask(question);
    }

    @Transactional
    @Operation(summary = "질문/답장/타입 직접 입력")
    @PostMapping("/insert")
    public void insert(@RequestBody ChatAIDTO.insertRequest req) {
        log.info("req = " + req);
        ChatType type = chatTypeService.getType(req.getType());
        String answerId = chatAnswerService.getAnswerId(req.getAnswer(), type);

        chatAIService.insertQuestion(req.getQuestion(), answerId);
    }

    @Operation(summary = "질문/답장/타입 전체 조회")
    @PostMapping("/readAll")
    public List<ChatAIQuestionDTO> readAll(){
        return chatAIService.readAll();
    }

    @Operation(summary = "질문 삭제")
    @PostMapping("/delete")
    public void deleteAll(@RequestParam String question){
        chatAIService.delete(question);
    }


//
//
//    @PostMapping("/ask")
//    public ChatAIDTO.Response askQuestion(String question) throws Exception {
//        return chatAIService.ask(question);
//    }
//
//
//    @PostMapping("/insert")
//    public String insert(ChatAIDTO.insertRequest req) throws Exception {
//        log.info("req = " + req);
//        return chatAIService.insertQuestion(req);
//    }
//
////    @PostMapping("/update")
////    public String update(ChatAIDTO.updateRequest req) throws Exception {
////        log.info("req = " + req);
////        return chatAIService.updateQuestion(req);
////    }
//
//
//
////    @Operation(summary = "유사 질문 등록")
////    @PostMapping("/similarityInsert")
////    public Map<String, String> similarityInsert(String question, String question2) throws Exception {
////        return chatAIService.similartiyInsert(question,question2);
////    }
//
//    @Operation(summary = "질문 답장 조회")
//    @PostMapping("/read")
//    public Object read(String question){
//        return chatAIService.read(question);
//    }
//
//    @Operation(summary = "답변 미등록 질문 조회")
//    @PostMapping("/getUnansweredQuestions")
//    public Map<String,Map<String,String>> getUnansweredQuestions(){return chatAIService.getUnansweredQuestions();}
//
//    @Operation(summary = "Fail 질문 조회")
//    @PostMapping("/getFailQuestions")
//    public Map<String,Map<String,String>> getFailQuestions(){
//        return chatAIService.getFailQuestions();
//    }
//
////    @Operation(summary = "유사 질문 전체 조회")
////    @PostMapping("/similarityReadAll")
////    public Map<String, Map<String, String>> similarityReadAll(){
////        return chatAIService.readSimilartiyAll();
////    }
//
//    @Operation(summary = "질문 삭제")
//    @PostMapping("/delete")
//    public void delete(String question) {
//        chatAIService.delete(question);
//    }
//
//    @Operation(summary = "답변 삭제")
//    @PostMapping("/deleteAnswer")
//    public void deleteAnswer(String question,String answer) {
//        chatAIService.deleteAnsewr(question,answer);
//    }
//
//
////    @PostMapping("/test")
////    public String test(String test){
////        return chatAIInfo.validateArea(test);
////    }
}
