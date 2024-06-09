package com.YH.yeohaenghama.domain.chat.repo;

import com.YH.yeohaenghama.domain.chat.DTO.ChatLogDTO;
import com.YH.yeohaenghama.domain.chat.model.ChatLog;
import com.YH.yeohaenghama.domain.chat.model.ChatMessage;
import com.YH.yeohaenghama.domain.chat.model.ChatRoom;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class ChatLogRepository {
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, ChatLog> opsHashChatRoom;
    private static final String CHAT_LOG = "CHAT_LOG";

    @PostConstruct
    public void init() {
        opsHashChatRoom = redisTemplate.opsForHash();
    }

    public ChatLog addChatLog(ChatLogDTO.Request dto) {
        ChatLog chatLog = opsHashChatRoom.get(CHAT_LOG, dto.getRoomId());
        if (chatLog == null) {
            chatLog = new ChatLog(dto.getRoomId());
        }

        List<ChatLogDTO.Response> messages = chatLog.getMessages();
        if (messages == null) {
            messages = new ArrayList<>();
        }

        ChatLogDTO.Response newMessage = new ChatLogDTO.Response();
        newMessage.setMessage(dto.getMessage());
        newMessage.setType(dto.getType());
        newMessage.setSender(dto.getSender());
        newMessage.setDateTime(LocalDateTime.now());
        messages.add(newMessage);

        chatLog.setMessages(messages);

        opsHashChatRoom.put(CHAT_LOG, dto.getRoomId(), chatLog);

        return chatLog;
    }





    public ChatLog findRoomById(String id) {
        return opsHashChatRoom.get(CHAT_LOG, id);
    }

}
