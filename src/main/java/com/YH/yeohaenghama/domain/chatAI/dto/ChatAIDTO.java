package com.YH.yeohaenghama.domain.chatAI.dto;

import com.YH.yeohaenghama.domain.chatAI.entity.ChatAI;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

public class ChatAIDTO {
    @Schema(name = "ChatAIDTO_Request") @Data
    public static class Request{
        private String question;
    }

    @Schema(name = "ChatAIDTO_Response") @Data
    public static class Response{
        private String question;
        private String answer;
        private String type;
        private Object result;
        private List<Map.Entry<String, Double>> other;

        public static Response toResponse(String question, String answer,List<Map.Entry<String, Double>> other){
            Response response = new Response();
            response.setQuestion(question);
            response.setAnswer(answer);
            response.setOther(other);
            return response;
        }

        public void setTypeAndResult(String type,Object result,String keyword){
            if(result == null){
                this.setAnswer("해당 정보를 찾을 수 없습니다");
            } else {
                this.setAnswer("해당 정보를 찾는 것이 맞으신가요? : " + keyword);
            }
            this.setType(type);
            this.setResult(result);
        }

        public void fail(){
            this.answer = "다시 한 번 질문해주세요.";
        }
    }

    public static ChatAI toEntity(String question,String answer){
        return ChatAI.builder()
                .question(question)
                .answer(answer)
                .build();
    }
}
