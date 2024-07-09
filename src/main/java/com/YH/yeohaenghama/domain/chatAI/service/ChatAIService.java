package com.YH.yeohaenghama.domain.chatAI.service;


import com.YH.yeohaenghama.domain.search.dto.SearchDTO;
import com.YH.yeohaenghama.domain.search.dto.SearchDiaryDTO;
import com.YH.yeohaenghama.domain.search.service.SearchService;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;

import com.YH.yeohaenghama.domain.chatAI.dto.ChatAIDTO;
import com.YH.yeohaenghama.domain.chatAI.entity.ChatAI;
import com.YH.yeohaenghama.domain.chatAI.repository.ChatAIRepository;
import com.YH.yeohaenghama.domain.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.*;

import org.apache.commons.text.similarity.CosineSimilarity;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatAIService {
    private final ChatAIRepository chatAIRepository;
    private final ChatAIInfo chatAIInfo;

    public ChatAIDTO.Response ask(String question) throws Exception {

        List<ChatAI> questionList = chatAIRepository.findAll().entrySet().stream()
                .filter(entry -> entry.getValue() != null && !entry.getValue().equals("fail")) // 값이 null이 아닌 항목만 필터링
                .map(entry -> new ChatAI(entry.getKey(), entry.getValue())) // ChatAI 객체로 매핑
                .collect(Collectors.toList());

        // 코사인 유사도 계산
        String bestMatch = findBestMatch(question, questionList);

        // 유사도가 높은 순으로 정렬 후 상위 3개의 질문 선택
        List<Map.Entry<String, Double>> sortedList = calculateSimilarityAndSort(question, bestMatch, questionList);

        if (bestMatch == null) throw new NoSuchElementException("No similar question found.");

        // 찾은 가장 유사한 질문에 대한 답변 가져오기
        String answer = findAnswerByBestMatch(bestMatch, questionList);

        System.out.println("Qeust : " + question);
        System.out.println("Answer : " + answer);


        String keyword = "";

         ChatAIDTO.Response response = ChatAIDTO.Response.toResponse(question, answer, sortedList);


         if (answer.equals("showDiaryAll")) {
             response.setTypeAndResult(answer,chatAIInfo.showDiaryAll(),"전체 일기 조회");
         }
         else if (answer.equals("showDiaryTitle")){
             keyword = selectKeyword(question);
             System.out.println("Keyword : " +keyword);
             response.setTypeAndResult(answer,chatAIInfo.showDiaryTitle(keyword),"["+ keyword + "] 제목이 포함된 일기 검색");
         }
         else if (answer.equals("showDiaryPlace")){
             keyword = selectKeyword(question);
             response.setTypeAndResult(answer,chatAIInfo.showDiaryPlace(keyword),"[" + keyword + "] 장소가 포함된 일기 검색");
         }
         else if (answer.equals("fail")){
             response.fail();
         }





        if(chatAIRepository.findAnswer(question) == null) insertQuestion(question,answer);

        return response;
    }


    public String insertQuestion(String question,String answer) throws BadRequestException {
        chatAIRepository.save(question,answer);
        return question;
    }

    public String similartiyInsert(String question1,String question2) throws BadRequestException {
        String answer = chatAIRepository.findAnswer(question2);
        if (answer == null) chatAIRepository.save(question2,null);
        else {
            chatAIRepository.save(question1,answer);
            chatAIRepository.saveSimilarity(question1,question2);
        }

        return answer;
    }

    public String updateQuestion(String question, String answer){
        String ChatAI = chatAIRepository.findAnswer(question);
        if(ChatAI.isEmpty()) throw new NoSuchElementException("해당 질문은 존재하지 않습니다.");
        chatAIRepository.save(question,answer);
        return question;
    }

    public Object read(String question){
        String a = chatAIRepository.findAnswer(question);
        System.out.println(question + " : " + a);
        return chatAIRepository.findAnswer(question);
    }

    public Map<String, String> readAll(){
        return chatAIRepository.findAll();
    }

    public Map<String,Map<String,String>> readSimilartiyAll(){
        return chatAIRepository.findSimilarytiyAll();
    }

    public void delete(String question){
        chatAIRepository.delete(question);
    }



















    public String selectKeyword(String question){

        Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
        KomoranResult analyzeResultList = komoran.analyze(question);
        List<Token> tokenList = analyzeResultList.getTokenList();

        String keyword = "";
        for (Token token : tokenList) {
            if (token.getPos().equals("NNP")) {
                System.out.format(token.getMorph());
                keyword = token.getMorph();
            }
        }

        return keyword;
    }

    private String findBestMatch(String question, List<ChatAI> questionList) {
        CosineSimilarity cosineSimilarity = new CosineSimilarity();
        String bestMatch = null;
        double highestSimilarity = -1;

        for (ChatAI chatAI : questionList) {
            String storedQuestion = chatAI.getQuestion();
            double similarity = cosineSimilarity.cosineSimilarity(
                    vectorize(question), vectorize(storedQuestion));
            if (similarity > highestSimilarity) {
                highestSimilarity = similarity;
                bestMatch = storedQuestion;
            }
        }

        return bestMatch;
    }

    private List<Map.Entry<String, Double>> calculateSimilarityAndSort(String question, String bestMatch, List<ChatAI> questionList) {
        CosineSimilarity cosineSimilarity = new CosineSimilarity();
        Map<String, Double> similarityMap = new HashMap<>();

        for (ChatAI chatAI : questionList) {
            String storedQuestion = chatAI.getQuestion();
            double similarity = cosineSimilarity.cosineSimilarity(
                    vectorize(question), vectorize(storedQuestion));
            similarityMap.put(storedQuestion, similarity);
        }

        // 유사도가 높은 순으로 정렬
        List<Map.Entry<String, Double>> sortedList = similarityMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue((v1, v2) -> v2.compareTo(v1)))
                .limit(3)
                .collect(Collectors.toList());

        return sortedList;
    }

    private String findAnswerByBestMatch(String bestMatch, List<ChatAI> questionList) {
        return questionList.stream()
                .filter(chatAI -> chatAI.getQuestion().equals(bestMatch))
                .findFirst()
                .map(ChatAI::getAnswer)
                .orElse("No answer found.");
    }

    // 문자열을 벡터로 변환하는 도우미 함수
    private Map<CharSequence, Integer> vectorize(String text) {
        Map<CharSequence, Integer> vector = new HashMap<>();
        for (String token : tokenize(text)) {
            vector.put(token, vector.getOrDefault(token, 0) + 1);
        }
        return vector;
    }


    private List<String> tokenize(String text) {
        List<String> tokens = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(text, " ");
        while (tokenizer.hasMoreTokens()) {
            tokens.add(tokenizer.nextToken());
        }
        return tokens;
    }


}
