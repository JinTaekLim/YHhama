package com.YH.yeohaenghama.domain.chatAI.dto;

import com.YH.yeohaenghama.domain.chatAI.entity.ChatType;
import com.YH.yeohaenghama.domain.chatAI.entity.ChatAnswer;
import lombok.Data;


public class ChatAnswerDTO {

  @Data
  public static class Response{
    private Long id;
    private String answer;
    private String type;

    public static ChatAnswerDTO.Response of(ChatAnswer chatAnswer){
      ChatAnswerDTO.Response response = new Response();
      response.setId(chatAnswer.getId());
      response.setAnswer(chatAnswer.getAnswer());
      response.setType(chatAnswer.getType().getType());
      return response;
    }

    public static ChatAnswer toEntity(String answer, ChatType type){
      ChatAnswer response = new ChatAnswer();
      response.setAnswer(answer);
      response.setType(type);
      return response;
    }
  }

//  @Data
//  public static class Read{
//
//    private Long id;
//
////    public static class All{
////      private Long id;
////      private String answer;
////    }
////
////    public static class one{
////
////    }
//
//  }

  @Data
  public static class Update{
    private Long id;
    private String answer;
  }

  @Data
  public static class Delete{
    private Long id;
  }
}
