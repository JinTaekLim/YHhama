package com.YH.yeohaenghama.domain.chat.service;

import com.YH.yeohaenghama.domain.chat.model.ChatRoom;
import com.YH.yeohaenghama.domain.chat.repo.ChatRoomRepository;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {
    private final ItineraryRepository itineraryRepository;
    private final ChatRoomRepository chatRoomRepository;

    public ChatRoom createRoom(String itineraryId){
        Optional<Itinerary> itineraryOpt = itineraryRepository.findById(Long.valueOf(itineraryId));
        Itinerary itinerary = itineraryOpt.get();

        List<String> users = new ArrayList<>();
        users.add(String.valueOf(itinerary.getAccount().getId()));

        return chatRoomRepository.createChatRoom(itinerary.getName(),itineraryId,users);
    }

    public ChatRoom addUserToChatRoom(String roomId, String user) {

        return chatRoomRepository.addUserToChatRoom(roomId,user);
    }
}
