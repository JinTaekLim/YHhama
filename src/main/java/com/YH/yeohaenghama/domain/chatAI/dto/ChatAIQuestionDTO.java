package com.YH.yeohaenghama.domain.chatAI.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Data;

@Data
public class ChatAIQuestionDTO {
  private String question;
  private String answer;
  private String type;

  public static List<ChatAIQuestionDTO> ofList(Map<String, Map<String, String>> questionList) {
    List<ChatAIQuestionDTO> response = new ArrayList<>();

    for (Map.Entry<String, Map<String, String>> entry : questionList.entrySet()) {
      response.add(ChatAIQuestionDTO.of(entry));
    }
    return response;
  }

  public static ChatAIQuestionDTO of(Map.Entry<String, Map<String, String>> entry) {
    ChatAIQuestionDTO chatAIQuestionDTO = new ChatAIQuestionDTO();
    chatAIQuestionDTO.setQuestion(entry.getKey());

    Map<String, String> valueMap = entry.getValue();

    String key = valueMap.keySet().iterator().next();

    chatAIQuestionDTO.setAnswer(key);
    chatAIQuestionDTO.setType(valueMap.get(key));

    return chatAIQuestionDTO;
  }

}

