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
        Random random = new Random();

        List<ChatAI> questionList = chatAIRepository.findAll().entrySet().stream()
                .filter(entry -> entry.getValue() != null)
                .map(entry -> {
                    List<Map.Entry<String, String>> validEntries = entry.getValue().entrySet().stream()
                            .filter(innerEntry -> {
                                String value = innerEntry.getValue();
                                return value != null && !value.equals("fail") && !value.equals("classifying");
                            })
                            .toList();

                    if (validEntries.isEmpty()) {return null;}

                    Map.Entry<String, String> selectedEntry = validEntries.get(random.nextInt(validEntries.size()));
                    return new ChatAI(entry.getKey(), selectedEntry.getKey(), selectedEntry.getValue());
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());


        String bestMatch = findBestMatch(question, questionList);

        List<Map.Entry<String, Double>> sortedList = calculateSimilarityAndSort(question, bestMatch, questionList);

        ChatAIDTO.Response response;

        System.out.println("-----------");
        for( Map.Entry<String, Double> a : sortedList ){
            System.out.println(a.getValue() + " : " + a.getKey());
        }
        System.out.println("-----------");

        if (bestMatch == null) {
            response = ChatAIDTO.Response.toResponse(question,
                    "죄송합니다. 해당 질문을 이해하지 못 했습니다. 다시 한 번 질문해주세요 . ","classifying",sortedList);
            insertQuestion(new ChatAIDTO.insertRequest(question,"classifying",null));
            return response;
        }


        ChatAI chatAI = findChatAIByBestMatch(bestMatch, questionList);

        System.out.println("Qeust : " + question);
        System.out.println("Answer : " + chatAI.getAnswer());
        System.out.println("Type : " + chatAI.getType());


        response = chatAIInfo.check(question,chatAI.getAnswer(), chatAI.getType(),sortedList);

        if(chatAIRepository.findAnswer(question) == null) insertQuestion(new ChatAIDTO.insertRequest(question,chatAI.getAnswer(),chatAI.getType()));

        return response;
    }


    public String insertQuestion(ChatAIDTO.insertRequest req) {
        chatAIRepository.update(req.getQuestion(),req.getAnswer(),req.getType());
        return req.getQuestion();
    }

    public Map<String, String> similartiyInsert(String question1, String question2) throws BadRequestException {
        Map<String,String> answer = chatAIRepository.findAnswer(question2);
        if (answer == null) chatAIRepository.update(question2,null,null);
        else {
            Map.Entry<String,String> map = answer.entrySet().iterator().next();
            chatAIRepository.update(question1,map.getKey(),map.getValue());
            chatAIRepository.saveSimilarity(question1,question2);
        }

        return answer;
    }

//    public String updateQuestion(ChatAIDTO.updateRequest req){
//        chatAIRepository.update(req.getQuestion(),req.getAnswer(),req.getType());
//        return req.getQuestion();
//    }

    public Object read(String question){
        Map<String,String> a = chatAIRepository.findAnswer(question);
        System.out.println(question + " : " + a);
        return chatAIRepository.findAnswer(question);
    }

    public Map<String, Map<String, String>> readAll(){
        return chatAIRepository.findAll();
    }

    public Map<String,Map<String,String>> getFailQuestions(){
        Map<String,Map<String,String>> response = chatAIRepository.findAll();
        return response.entrySet().stream()
                .filter(entry -> entry.getValue() != null && entry.getValue().containsKey("fail"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<String, Map<String, String>> getUnansweredQuestions() {
        Map<String, Map<String, String>> response = chatAIRepository.findAll();

        return response.entrySet().stream()
                .filter(entry -> entry.getValue() != null && entry.getValue().containsKey("classifying") ||entry.getValue().containsValue("classifying"))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().entrySet().stream()
                                .filter(innerEntry -> "classifying".equals(innerEntry.getValue()))
                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                ));
    }

    public Map<String,Map<String,String>> readSimilartiyAll(){
        return chatAIRepository.findSimilarytiyAll();
    }

    public void delete(String question){
        chatAIRepository.delete(question);
    }

    public void deleteAnsewr(String qeustion, String answer) {
        chatAIRepository.deleteAnswer(qeustion,answer);
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
