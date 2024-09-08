package com.YH.yeohaenghama.domain.chatAI.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class ChatAIQuestionDTO {
  private String question;
  private String answerId;

  public static List<ChatAIQuestionDTO> ofList(Map<String, String> questionList) {
    List<ChatAIQuestionDTO> response = new ArrayList<>();

    for (Map.Entry<String, String> entry : questionList.entrySet()) {
      response.add(ChatAIQuestionDTO.of(entry));
    }
    return response;
  }

  public static ChatAIQuestionDTO of(Map.Entry<String, String> entry) {
    ChatAIQuestionDTO chatAIQuestionDTO = new ChatAIQuestionDTO();
    chatAIQuestionDTO.setQuestion(entry.getKey());
    chatAIQuestionDTO.setAnswerId(entry.getValue());
    return chatAIQuestionDTO;
  }
}
