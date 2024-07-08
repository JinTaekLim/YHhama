package com.YH.yeohaenghama.domain.chatAI.controller;

import com.YH.yeohaenghama.domain.chatAI.dto.ChatAIDTO;
import com.YH.yeohaenghama.domain.chatAI.service.ChatAIService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/chatAI")
@RequiredArgsConstructor
public class ChatAIController {

    private final ChatAIService chatAIService;


    @PostMapping("/ask")
    public ChatAIDTO.Response askQuestion(String question) throws Exception {
        return chatAIService.ask(question);
    }


    @PostMapping("/insert")
    public String insert(String question,String answer) throws Exception {
        return chatAIService.insertQuestion(question,answer);
    }

    @Operation(summary = "읽기")
    @PostMapping("/read")
    public Object read(String question){
        return chatAIService.read(question);
    }
    @Operation(summary = "전체 읽기")
    @PostMapping("/readAll")
    public Map<String, String> readAll(){
        return chatAIService.readAll();
    }

    @PostMapping("/delete")
    public void insert(String question) {
        chatAIService.delete(question);
    }


}
