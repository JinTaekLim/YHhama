package com.YH.yeohaenghama.domain.chatAI.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class ChatAI {

    private String question;
    private String answer;

    @Builder
    public ChatAI(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }
}
