package com.YH.yeohaenghama.domain.chatAI.service;


import com.YH.yeohaenghama.domain.chatAI.dto.ChatAIDTO;
import com.YH.yeohaenghama.domain.chatAI.entity.ChatAI;
import com.YH.yeohaenghama.domain.chatAI.repository.ChatAIRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.*;

import org.apache.commons.text.similarity.CosineSimilarity;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatAIService{
    private final ChatAIRepository chatAIRepository;
    private final ChatAIInfo chatAIInfo;

    public ChatAIDTO.Response ask(String question) throws Exception {

        List<ChatAI> questionList = chatAIRepository.findAll().entrySet().stream()
                .filter(entry -> entry.getValue() != null)
                .map(entry -> {
                    Map.Entry<String, String> map = entry.getValue().entrySet().iterator().next();

                    if (entry.getValue().size() > 1) {
                        for (Map.Entry<String, String> entryValue : entry.getValue().entrySet()) {
                            String value = entryValue.getValue();
                            if (value != null && !value.equals("fail")) {
                                map = entryValue;
                                break;
                            }
                        }
                    }

                    return new ChatAI(entry.getKey(), map.getKey(), map.getValue());
                })
                .collect(Collectors.toList());


        // 코사인 유사도 계산
        String bestMatch = findBestMatch(question, questionList);

        // 유사도가 높은 순으로 정렬 후 상위 3개의 질문 선택
        List<Map.Entry<String, Double>> sortedList = calculateSimilarityAndSort(question, bestMatch, questionList);

        ChatAIDTO.Response response;

        if (bestMatch == null) {
            response = ChatAIDTO.Response.toResponse(question,
                    "죄송합니다. 해당 질문 관련 데이터가 아직 등록되어있지 않습니다. ","fail",null);
            insertQuestion(new ChatAIDTO.insertRequest(question,"fail",null));
            return response;
        }

        // 찾은 가장 유사한 질문에 대한 답변 가져오기
        ChatAI chatAI = findChatAIByBestMatch(bestMatch, questionList);

        System.out.println("Qeust : " + question);
        System.out.println("Answer : " + chatAI.getAnswer());
        System.out.println("Type : " + chatAI.getType());


        response = chatAIInfo.check(question,chatAI.getAnswer(), chatAI.getType(),sortedList);

        if(chatAIRepository.findAnswer(question) == null) insertQuestion(new ChatAIDTO.insertRequest(question,chatAI.getAnswer(),chatAI.getType()));

        return response;
    }


    public String insertQuestion(ChatAIDTO.insertRequest req) {
        chatAIRepository.save(req.getQuestion(),req.getAnswer(),req.getType());
        return req.getQuestion();
    }

    public Map<String, String> similartiyInsert(String question1, String question2) throws BadRequestException {
        Map<String,String> answer = chatAIRepository.findAnswer(question2);
        if (answer == null) chatAIRepository.save(question2,null,null);
        else {
            Map.Entry<String,String> map = answer.entrySet().iterator().next();
            chatAIRepository.save(question1,map.getKey(),map.getValue());
            chatAIRepository.saveSimilarity(question1,question2);
        }

        return answer;
    }

    public String updateQuestion(ChatAIDTO.updateRequest req){
        chatAIRepository.update(req.getQuestion(),req.getAnswer());
        return req.getQuestion();
    }

    public Object read(String question){
        Map<String,String> a = chatAIRepository.findAnswer(question);
        System.out.println(question + " : " + a);
        return chatAIRepository.findAnswer(question);
    }

    public Map<String, Map<String, String>> readAll(){
        return chatAIRepository.findAll();
    }

    public Map<String,Map<String,String>> getUnansweredQuestions(){
        Map<String,Map<String,String>> response = chatAIRepository.findAll();
        return response.entrySet().stream()
                .filter(entry -> entry.getValue() != null && entry.getValue().containsKey("fail"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<String,Map<String,String>> readSimilartiyAll(){
        return chatAIRepository.findSimilarytiyAll();
    }

    public void delete(String question){
        chatAIRepository.delete(question);
    }




















    private String findBestMatch(String question, List<ChatAI> questionList) {
        CosineSimilarity cosineSimilarity = new CosineSimilarity();
        String bestMatch = null;
        double highestSimilarity = 0.5;

        for (ChatAI chatAI : questionList) {

            String storedQuestion = chatAI.getQuestion();
            double similarity = cosineSimilarity.cosineSimilarity(
                    vectorize(question), vectorize(storedQuestion));
            if (similarity > highestSimilarity && !chatAI.getAnswer().equals("fail")) {
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

    private ChatAI findChatAIByBestMatch(String bestMatch, List<ChatAI> questionList) {
        return questionList.stream()
                .filter(chatAI -> chatAI.getQuestion().equals(bestMatch))
                .findFirst()
                .orElse(null);
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
