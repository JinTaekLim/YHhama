package com.YH.yeohaenghama.domain.chatAI.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import java.util.Map;


@Repository
public class ChatAIRepository {

    private static final String KEY = "chatAI";

    private final HashOperations<String, String, String> hashOperations;

    @Autowired
    public ChatAIRepository(RedisTemplate<String, Object> redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void save(String question, String answer) {
        hashOperations.put(KEY, question, answer);
    }

    public String findAnswer(String question) {
        return hashOperations.get(KEY, question);
    }

    public Map<String, String> findAll() {
        return hashOperations.entries(KEY);
    }

    public void delete(String question) {
        hashOperations.delete(KEY, question);
    }
}
