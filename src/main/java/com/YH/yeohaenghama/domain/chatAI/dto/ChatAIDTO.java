package com.YH.yeohaenghama.domain.chatAI.dto;

import com.YH.yeohaenghama.domain.chatAI.entity.ChatAI;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

public class ChatAIDTO {

    @Schema(name = "ChatAIDTO_insertRequest") @Data
    public static class insertRequest{
        private String question;
        private String answer;
        private String type;

        public insertRequest(String question, String answer, String tye){
            this.question = question;
            this.answer = answer;
            this.type = tye;
        }
    }

    @Schema(name = "ChatAIDTO_Response") @Data
    public static class Response{
        private String question;
        private String answer;
        private String type;
        private Object result;
        private List<Map.Entry<String, Double>> other;

        public static Response toResponse(String question, String answer, String type, List<Map.Entry<String, Double>> other){
            Response response = new Response();
            response.setQuestion(question);
            response.setAnswer(answer);
            response.setType(type);
            response.setOther(other);
            return response;
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
