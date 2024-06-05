package com.YH.yeohaenghama.domain.chat.service;

import com.YH.yeohaenghama.domain.chat.DTO.ChatLogDTO;
import com.YH.yeohaenghama.domain.chat.model.ChatLog;
import com.YH.yeohaenghama.domain.chat.model.ChatRoom;
import com.YH.yeohaenghama.domain.chat.repo.ChatLogRepository;
import com.YH.yeohaenghama.domain.chat.repo.ChatRoomRepository;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {
    private final ItineraryRepository itineraryRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatLogRepository chatLogRepository; // ChatLogRepository 추가


    public ChatRoom createChatRomm(String itineraryId){
        Optional<Itinerary> itineraryOpt = itineraryRepository.findById(Long.valueOf(itineraryId));
        Itinerary itinerary = itineraryOpt.get();

        List<String> users = new ArrayList<>();
        users.add(String.valueOf(itinerary.getAccount().getId()));
        ChatRoom chatRoom = chatRoomRepository.createChatRoom(itinerary.getName(), itineraryId, users);
        return chatRoom;
    }

    public ChatLog addChatLog(ChatLogDTO.Request dto){
        return chatLogRepository.addChatLog(dto);
    }

//    private void createChatLog(String roomId, List<Map<String, String>> messages) {
//        ChatLog chatLog = new ChatLog(roomId, messages);
//        chatLogRepository.save(chatLog);
//    }
    public ChatRoom addUserToChatRoom(String roomId, String user) {
        return chatRoomRepository.addUserToChatRoom(roomId,user);
    }

    public ChatRoom findRoom(String roomId){
        return chatRoomRepository.findRoomById(roomId);
    }

    public ChatLog findChatLog(String roomId){
        return chatLogRepository.findRoomById(roomId);
    }
}
