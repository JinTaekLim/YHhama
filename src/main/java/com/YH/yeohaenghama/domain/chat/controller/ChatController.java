package com.YH.yeohaenghama.domain.chat.controller;

import com.YH.yeohaenghama.domain.chat.DTO.ChatLogDTO;
import com.YH.yeohaenghama.domain.chat.model.ChatMessage;
import com.YH.yeohaenghama.domain.chat.pubsub.RedisPublisher;
import com.YH.yeohaenghama.domain.chat.repo.ChatLogRepository;
import com.YH.yeohaenghama.domain.chat.repo.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final RedisPublisher redisPublisher;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatLogRepository chatLogRepository;

    /**
     * websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
     */
    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            chatRoomRepository.enterChatRoom(message.getRoomId());
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
        } else if (ChatMessage.MessageType.TALK.equals(message.getType())) {
            ChatLogDTO.Request request = new ChatLogDTO.Request();
            request.setMessage(message.getMessage());
            request.setSender(message.getSender());
            request.setRoomId(message.getRoomId());
            chatLogRepository.addChatLog(request);
        }

        redisPublisher.publish(chatRoomRepository.getTopic(message.getRoomId()), message);
    }

}
