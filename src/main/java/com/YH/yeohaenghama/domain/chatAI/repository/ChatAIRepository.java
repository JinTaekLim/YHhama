package com.YH.yeohaenghama.domain.chatAI.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.cache.interceptor.SimpleKeyGenerator.generateKey;


@Repository
public class ChatAIRepository {

    private static final String KEY = "chatAI";
    private static final String SimilarityKey = "SimilarityKey";

    private final HashOperations<String, String, String> hashOperations;
    private final HashOperations<String, String, Map<String, String>> hashOperations2;
    private final RedisTemplate<String, Object> redisTemplate;



    @Autowired
    public ChatAIRepository(RedisTemplate<String, Object> redisTemplate, RedisTemplate<String, Object> redisTemplate1) {
        this.hashOperations = redisTemplate.opsForHash();
        this.hashOperations2 = redisTemplate.opsForHash();
        this.redisTemplate = redisTemplate;
    }

    public void save(String question, String answer) {
        hashOperations.put(KEY, question, answer);
    }
    public void saveSimilarity(String question1, String question2) {
        String generatedKey = generateUniqueKey();
        Map<String, String> mapValue = new HashMap<>();
        mapValue.put(question1, question2);
        hashOperations2.put(SimilarityKey, generatedKey, mapValue);
    }

    public String findAnswer(String question) {
        return hashOperations.get(KEY, question);
    }

    public Map<String, String> findAll() {
        return hashOperations.entries(KEY);
    }

    public Map<String,Map<String,String>> findSimilarytiyAll(){
        return hashOperations2.entries(SimilarityKey);
    }

    public void delete(String question) {
        hashOperations.delete(KEY, question);
    }

    private String generateUniqueKey() {
        Long uniqueKey = redisTemplate.opsForValue().increment(KEY + ":counter", 1);
        return String.valueOf(uniqueKey);
    }
}
