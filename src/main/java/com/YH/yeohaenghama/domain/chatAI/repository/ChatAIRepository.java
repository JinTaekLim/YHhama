package com.YH.yeohaenghama.domain.chatAI.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;


@Repository
public class ChatAIRepository {

    private static final String KEY = "chatAI";
    private static final String SimilarityKey = "SimilarityKey";

    private final HashOperations<String, String, Map<String, String>> hashOperations;
    private final RedisTemplate<String, Object> redisTemplate;



    @Autowired
    public ChatAIRepository(RedisTemplate<String, Object> redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
        this.redisTemplate = redisTemplate;
    }
//
//    public void save(String question, String answer, String type) {
//        Map<String, String> mapValue = new HashMap<>();
//        mapValue.put(answer, type);
//        hashOperations.put(KEY, question,mapValue);
//    }

    public void update(String question, String answer,String type){
        Map<String, String> existingMap = hashOperations.get(KEY, question);

        if (existingMap == null) {
            existingMap = new HashMap<>();
        }
        existingMap.remove("fail");

        existingMap.put(answer, type);

        hashOperations.put(KEY, question, existingMap);
    }


    public void deleteAnswer(String question, String answer) {
        Map<String, String> existingMap = hashOperations.get(KEY, question);

        if (existingMap != null && existingMap.containsKey(answer)) {
            existingMap.remove(answer);
            if (existingMap.isEmpty()) {
                hashOperations.delete(KEY, question);
            } else {
                hashOperations.put(KEY, question, existingMap);
            }
        }
    }



    public void saveSimilarity(String question1, String question2) {
        String generatedKey = generateUniqueKey();
        Map<String, String> mapValue = new HashMap<>();
        mapValue.put(question1, question2);
        hashOperations.put(SimilarityKey, generatedKey, mapValue);
    }

    public Map<String, String> findAnswer(String question) {
        return hashOperations.get(KEY, question);
    }

    public Map<String, Map<String, String>> findAll() {
        return hashOperations.entries(KEY);
    }

    public Map<String,Map<String,String>> findSimilarytiyAll(){
        return hashOperations.entries(SimilarityKey);
    }

    public void delete(String question) {
        hashOperations.delete(KEY, question);
    }

    private String generateUniqueKey() {
        Long uniqueKey = redisTemplate.opsForValue().increment(KEY + ":counter", 1);
        return String.valueOf(uniqueKey);
    }
}
