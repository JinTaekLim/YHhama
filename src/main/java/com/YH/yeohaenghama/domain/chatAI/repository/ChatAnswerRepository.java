//package com.YH.yeohaenghama.domain.chatAI.repository;
//
//import com.YH.yeohaenghama.domain.chatAI.entity.ChatAnswer;
//import com.YH.yeohaenghama.domain.chatAI.entity.ChatType;
//import java.util.List;
//import java.util.Optional;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//
//public interface ChatAnswerRepository extends JpaRepository<ChatAnswer, Long> {
//
//  Optional<ChatAnswer> findByAnswer(String answer);
//  Optional<ChatAnswer> findByType(ChatType chatType);
//}
