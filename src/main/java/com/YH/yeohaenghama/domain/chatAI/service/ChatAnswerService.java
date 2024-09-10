package com.YH.yeohaenghama.domain.chatAI.service;

import com.YH.yeohaenghama.domain.chatAI.dto.ChatAnswerDTO;
import com.YH.yeohaenghama.domain.chatAI.entity.ChatAnswer;
import com.YH.yeohaenghama.domain.chatAI.entity.ChatType;
import com.YH.yeohaenghama.domain.chatAI.repository.ChatAnswerRepository;
import com.YH.yeohaenghama.domain.chatAI.repository.ChatTypeRepository;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatAnswerService {

  private final ChatAnswerRepository chatAnswerRepository;


  public ChatAnswer insert(String answer, ChatType type){

    ChatAnswer chatAnswer = ChatAnswerDTO.Response.toEntity(answer, type);
    return chatAnswerRepository.save(chatAnswer);
  }

  public ChatAnswer readOne(Long id){
    return chatAnswerRepository.findById(id).orElse(null);
  }

  public List<ChatAnswer> readALl(){
    return chatAnswerRepository.findAll();
  }

  public ChatAnswer update(ChatAnswerDTO.Update req){
    ChatAnswer chatAnswer = chatAnswerRepository.findById(req.getId())
        .orElseThrow(() -> new NoSuchElementException("답변을 찾을 수 없습니다."));

    chatAnswer.setAnswer(req.getAnswer());
    return chatAnswerRepository.save(chatAnswer);
  }

  public String getAnswerId(String answer, ChatType type) {

    ChatAnswer chatAnswer = chatAnswerRepository.findByAnswer(answer)
        .orElseGet(() -> {
          ChatAnswer newChatAnswer = ChatAnswerDTO.Response.toEntity(answer, type);
          chatAnswerRepository.save(newChatAnswer);
          return newChatAnswer;
        });

    return String.valueOf(chatAnswer.getId());
  }

  public ChatAnswer getAnswer(String answer,ChatType chatType){
    return chatAnswerRepository.findByType(chatType).orElseGet(() -> {
      ChatAnswer newChatAnswer = new ChatAnswer();
      newChatAnswer.setAnswer(answer);
      newChatAnswer.setType(chatType);
      chatAnswerRepository.save(newChatAnswer);
      return newChatAnswer;
    });
  }

}
