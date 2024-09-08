package com.YH.yeohaenghama.domain.chatAI.service;

import com.YH.yeohaenghama.domain.chatAI.dto.ChatAnswerDTO;
import com.YH.yeohaenghama.domain.chatAI.entity.ChatAnswer;
import com.YH.yeohaenghama.domain.chatAI.entity.ChatType;
import com.YH.yeohaenghama.domain.chatAI.repository.ChatTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatTypeService {

  private final ChatTypeRepository chatTypeRepository;


  public ChatType getType(String type) {
    return chatTypeRepository.findByType(type)
        .orElseGet(() -> {
          ChatType newChatType = new ChatType(type);
          chatTypeRepository.save(newChatType);
          return newChatType;
        });
  }

}
