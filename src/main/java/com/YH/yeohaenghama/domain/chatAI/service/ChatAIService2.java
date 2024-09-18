package com.YH.yeohaenghama.domain.chatAI.service;

import com.YH.yeohaenghama.domain.chatAI.dto.ChatAIDTO;
import com.YH.yeohaenghama.domain.chatAI.repository.ChatAIRepository;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatAIService2 {

  private final ChatAIRepository chatAIRepository;
  private final ChatAIInfo2 chatAIInfo2;

  double similarity = 0.6;


/*
1. 질문을 전달받는다.
2. 등록되어있는 다른 질문과 비교하여 유사한 모든 질문을 반환한다.
3. 이중 랜덤으로 하나의 질문을 선택하고, 해당 질문의 type을 가져온다.
4. 해당 Type에 대한 답장을 전달한다.
 */

  public ChatAIDTO.Response ask(String question) {
    Map<String, Map<String, String>> similarityQuestion = SimilarityAnalysis(question);

    if (similarityQuestion.isEmpty()) {
      return chatAIInfo2.fail(question);
    } else {
      return chatAIInfo2.success(question, similarityQuestion);
    }
  }



  private Map<String, Map<String, String>> SimilarityAnalysis(String question) {
    Map<String, Map<String, String>> questionList = chatAIRepository.findAll();

    if (questionList == null || questionList.isEmpty()) return new HashMap<>();
    Map<String, Map<String, String>> response = new HashMap<>();

    // 질문 유사도 판별
    questionList.forEach((comparative, value) -> {
      if (jaccardSimilarity(question, comparative) > similarity) {
        response.put(comparative, value);
      }
    });

    return response;
  }











  public static double jaccardSimilarity(String s1, String s2) {
    Set<String> set1 = Arrays.stream(s1.split("\\s+")).collect(Collectors.toSet());
    Set<String> set2 = Arrays.stream(s2.split("\\s+")).collect(Collectors.toSet());

    Set<String> intersection = new HashSet<>(set1);
    intersection.retainAll(set2);

    Set<String> union = new HashSet<>(set1);
    union.addAll(set2);

    return (double) intersection.size() / union.size();
  }



  public void insertQuestion(String question, String answer, String type) {

    chatAIRepository.update(question, answer, type);
  }

  public Map<String, Map<String, String>> readAll() {
    return chatAIRepository.findAll();
  }

  public void delete(String question){
    chatAIRepository.deleteQuestion(question);
  }

}
