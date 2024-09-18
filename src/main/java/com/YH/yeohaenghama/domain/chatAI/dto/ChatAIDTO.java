package com.YH.yeohaenghama.domain.chatAI.dto;

import com.YH.yeohaenghama.domain.chatAI.entity.ChatAI;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

public class ChatAIDTO {
    @Schema(name = "ChatAIDTO_Request") @Data
    public static class Request{
        private String question;
    }

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

    @Schema(name = "ChatAIDTO_updateRequest") @Data
    public static class updateRequest{
        private String question;
        private String answer;
        private String type;

        public updateRequest(String question, String answer,String type){
            this.question = question;
            this.answer = answer;
            this.type = type;
        }
    }

    @Schema(name = "ChatAIDTO_Response") @Data @Builder
    public static class Response{
        private String question;
        private String answer;
        private String type;
        private Object result;
        private Map<String, Map<String, String>> other;

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
