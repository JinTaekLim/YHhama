package com.YH.yeohaenghama.domain.chatAI.service;

import com.YH.yeohaenghama.domain.chatAI.dto.ChatAIDTO;
import com.YH.yeohaenghama.domain.chatAI.dto.ChatAIDTO.Response;
import com.YH.yeohaenghama.domain.chatAI.dto.ChatAIQuestionDTO;
import com.YH.yeohaenghama.domain.chatAI.dto.ChatAnswerDTO;
import com.YH.yeohaenghama.domain.chatAI.entity.ChatAI;
import com.YH.yeohaenghama.domain.chatAI.entity.ChatAnswer;
import com.YH.yeohaenghama.domain.chatAI.entity.ChatType;
import com.YH.yeohaenghama.domain.chatAI.repository.ChatAIRepository;
import com.YH.yeohaenghama.domain.chatAI.repository.ChatAnswerRepository;
import com.YH.yeohaenghama.domain.chatAI.repository.ChatTypeRepository;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.similarity.CosineSimilarity;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatAIService2 {

  private final ChatAIRepository chatAIRepository;
  private final ChatAnswerRepository chatAnswerRepository;
  private final ChatAIInfo2 chatAIInfo2;

  Random random = new Random();
  double similarity = 0.9;


/*
1. 질문을 전달받는다.
2. 등록되어있는 다른 질문과 비교하여 유사한 모든 질문을 반환한다.
3. 이중 랜덤으로 하나의 질문을 선택하고, 해당 질문의 type을 가져온다.
4. 해당 Type에 대한 답장을 전달한다.
 */

  public ChatAIDTO.Response ask(String question){

    List<ChatAIQuestionDTO> similarityQuestion = SimilarityAnalysis(question); // 유사한 모든 질문 반환

    if (similarityQuestion.isEmpty()) return chatAIInfo2.fail(question); // 유사한 질문이 없을때

    ChatAnswer chatAnswer = getAnswer(similarityQuestion);  // 이후 하나의 답장 반환

    return chatAIInfo2.success(question,chatAnswer,similarityQuestion);
  }



  private List<ChatAIQuestionDTO> SimilarityAnalysis(String question){

    Map<String,String> questionList = chatAIRepository.findAll();
    Map<String,String> response = new HashMap<>();

    // 질문 유사도 판별
    questionList.forEach((comparative, value) -> {
      if (jaccardSimilarity(question, comparative) > similarity) {
        response.put(comparative, value);
      }
    });


    return ChatAIQuestionDTO.ofList(response);
  }



  private ChatAnswer getAnswer(List<ChatAIQuestionDTO> questionList){

    if(questionList.isEmpty()) throw new NoSuchElementException();

    int randomIndex = random.nextInt(questionList.size());

    ChatAIQuestionDTO question = questionList.get(randomIndex);
    Long questionId = Long.valueOf(question.getAnswerId());
    return chatAnswerRepository.findById(questionId)
        .orElseThrow(() -> new NoSuchElementException("No ChatAnswer found for ID: " + questionId));
  }

  private String getType(ChatAnswer question){
    return null;
  }

  private String resultType(ChatType type){
    return null;
  }


  // 형태소 분석기
//  private String getKeyword(String question){
//
//    Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
//    KomoranResult analyzeResultList = komoran.analyze(question);
//    List<Token> tokenList = analyzeResultList.getTokenList();
//
//    for(Token token : tokenList) {
//      String pos = token.getPos();
//
//      if (pos.startsWith("NN") || pos.startsWith("VV") || pos.startsWith("VA") || pos.startsWith("RB")) {
//        System.out.println("["+token.getPos()+"]"+ " " + token.getMorph());
//      }
//      System.out.print(token.getBeginIndex() + " ");
//      System.out.print(token.getEndIndex());
//      System.out.print(token.getMorph() + " ");
//      System.out.println(token.getPos());
//    }
//    return null;
//  }
//









  // 질문 유사도 판별



  public static double jaccardSimilarity(String s1, String s2) {
    Set<String> set1 = new HashSet<>(Set.of(s1.split("\\s+")));
    Set<String> set2 = new HashSet<>(Set.of(s2.split("\\s+")));

    Set<String> intersection = new HashSet<>(set1);
    intersection.retainAll(set2);

    Set<String> union = new HashSet<>(set1);
    union.addAll(set2);

    return (double) intersection.size() / union.size();
  }

//  // 단순한 토큰화 함수 (공백 기준으로 단어 분리)
//  public static String[] tokenize(String text) {
//    return text.split("\\s+");
//  }
//
//  // 문장을 TF 벡터로 변환
//  public static Map<String, Integer> termFrequency(String[] tokens) {
//    Map<String, Integer> tf = new HashMap<>();
//    for (String token : tokens) {
//      tf.put(token, tf.getOrDefault(token, 0) + 1);
//    }
//    return tf;
//  }
//
//  // 두 벡터의 코사인 유사도를 계산
//  public static double cosineSimilarity(double[] vectorA, double[] vectorB) {
//    double dotProduct = 0.0;
//    double normA = 0.0;
//    double normB = 0.0;
//
//    for (int i = 0; i < vectorA.length; i++) {
//      dotProduct += vectorA[i] * vectorB[i];
//      normA += Math.pow(vectorA[i], 2);
//      normB += Math.pow(vectorB[i], 2);
//    }
//
//    return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
//  }
//
//  // 두 질문 간의 코사인 유사도를 계산하는 함수
//  public static double calculateCosineSimilarity(String questionA, String questionB) {
//    String[] tokensA = tokenize(questionA);
//    String[] tokensB = tokenize(questionB);
//
//    Map<String, Integer> tfA = termFrequency(tokensA);
//    Map<String, Integer> tfB = termFrequency(tokensB);
//
//    // 전체 단어 목록 생성
//    Map<String, Integer> allTerms = new HashMap<>();
//    int index = 0;
//    for (String term : tfA.keySet()) {
//      if (!allTerms.containsKey(term)) {
//        allTerms.put(term, index++);
//      }
//    }
//    for (String term : tfB.keySet()) {
//      if (!allTerms.containsKey(term)) {
//        allTerms.put(term, index++);
//      }
//    }
//
//    // 두 문장을 벡터로 변환
//    double[] vectorA = new double[allTerms.size()];
//    double[] vectorB = new double[allTerms.size()];
//
//    for (Map.Entry<String, Integer> entry : tfA.entrySet()) {
//      vectorA[allTerms.get(entry.getKey())] = entry.getValue();
//    }
//
//    for (Map.Entry<String, Integer> entry : tfB.entrySet()) {
//      vectorB[allTerms.get(entry.getKey())] = entry.getValue();
//    }
//
//    // 코사인 유사도 계산
//    return cosineSimilarity(vectorA, vectorB);
//  }
//


  // 질문 유사도 판별








  public void insertQuestion(String question, String answerId) {

    chatAIRepository.update(question, answerId);;
  }

  public List<ChatAIQuestionDTO> readAll(){
    List<ChatAIQuestionDTO> response = new ArrayList<>();

    Map<String,String> list = chatAIRepository.findAll();

    for (Map.Entry<String, String> entry : list.entrySet()) {

      ChatAIQuestionDTO dto = new ChatAIQuestionDTO();
      dto.setQuestion(entry.getKey());
      ChatAnswer answer = chatAnswerRepository.findById(Long.valueOf(entry.getValue()))
          .orElse(null);
      dto.setAnswerId(answer.getAnswer());
      response.add(dto);
    }
    return response;
  }

  public void delete(String question){
    chatAIRepository.deleteQuestion(question);
  }

}
