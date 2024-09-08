package com.YH.yeohaenghama.domain.chatAI.controller;

import com.YH.yeohaenghama.common.apiResult.ApiResult;
import com.YH.yeohaenghama.domain.chatAI.dto.ChatAnswerDTO;
import com.YH.yeohaenghama.domain.chatAI.entity.ChatAnswer;
import com.YH.yeohaenghama.domain.chatAI.entity.ChatType;
import com.YH.yeohaenghama.domain.chatAI.service.ChatAnswerService;
import com.YH.yeohaenghama.domain.chatAI.service.ChatTypeService;
import com.google.protobuf.Api;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chatAnswer")
@RequiredArgsConstructor
@Slf4j
public class ChatAnswerController {


  private final ChatAnswerService chatAnswerService;
  private final ChatTypeService chatTypeService;

  @PostMapping("/insert")
  public ChatAnswer answerInsert(@RequestParam String answer, @RequestParam String type){
    ChatType chatType = chatTypeService.getType(type);
    return chatAnswerService.insert(answer, chatType);
  }

  @GetMapping("/readOne/{id}")
  public ApiResult<ChatAnswer> answerRead(@PathVariable Long id){
    return ApiResult.success(chatAnswerService.readOne(id));
  }


  @GetMapping("/readAll")
  public ApiResult<List<ChatAnswer>> answerReadAll(){
    return ApiResult.success(chatAnswerService.readALl());
  }
  public String answerUpdate(){
    return null;
  }
  public String answerDelete(){

    return null;
  }

}
