package com.YH.yeohaenghama.domain.chatAI.controller;

import com.YH.yeohaenghama.domain.chatAI.dto.ChatAIDTO;
import com.YH.yeohaenghama.domain.chatAI.service.ChatAIService2;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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


    @PostMapping("/ask")
    public ChatAIDTO.Response askQuestion(String question){
        return chatAIService.ask(question);
    }

    @Transactional
    @Operation(summary = "질문/답장/타입 직접 입력")
    @PostMapping("/insert")
    public void insert(@RequestBody ChatAIDTO.insertRequest req) {
        log.info("req = " + req);

        chatAIService.insertQuestion(req.getQuestion(), req.getAnswer(), req.getType());
    }

    @Operation(summary = "질문/답장/타입 전체 조회")
    @PostMapping("/readAll")
    public Map<String, Map<String, String>> readAll(){
        return chatAIService.readAll();
    }

    @Operation(summary = "질문 삭제")
    @PostMapping("/delete")
    public void deleteAll(@RequestParam String question){
        chatAIService.delete(question);
    }
}
