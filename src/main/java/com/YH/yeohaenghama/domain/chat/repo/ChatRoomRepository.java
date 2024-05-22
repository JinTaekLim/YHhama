package com.YH.yeohaenghama.domain.chat.repo;

import com.YH.yeohaenghama.domain.chat.model.ChatRoom;
import com.YH.yeohaenghama.domain.chat.pubsub.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class ChatRoomRepository {
    // 채팅방(topic)에 발행되는 메시지를 처리할 Listner
    private final RedisMessageListenerContainer redisMessageListener;
    // 구독 처리 서비스
    private final RedisSubscriber redisSubscriber;
    // Redis
    private static final String CHAT_ROOMS = "CHAT_ROOM";
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, ChatRoom> opsHashChatRoom;
    // 채팅방의 대화 메시지를 발행하기 위한 redis topic 정보. 서버별로 채팅방에 매치되는 topic정보를 Map에 넣어 roomId로 찾을수 있도록 한다.
    private Map<String, ChannelTopic> topics;

    @PostConstruct
    private void init() {
        opsHashChatRoom = redisTemplate.opsForHash();
        topics = new HashMap<>();
    }




    public List<ChatRoom> getChatRoomsByUserId(String userId) {
        List<ChatRoom> allChatRooms = opsHashChatRoom.values(CHAT_ROOMS);
        return allChatRooms.stream()
                .filter(chatRoom -> chatRoom.getUsers().contains(userId))
                .collect(Collectors.toList());
    }

    public List<ChatRoom> findAllRoom() {
        return opsHashChatRoom.values(CHAT_ROOMS);
    }

    public ChatRoom findRoomById(String id) {
        return opsHashChatRoom.get(CHAT_ROOMS, id);
    }

    public ChatRoom findRoomByItineraryId(String itineraryId) { return opsHashChatRoom.get(CHAT_ROOMS,itineraryId); }


    /**
     * 채팅방 생성 : 서버간 채팅방 공유를 위해 redis hash에 저장한다.
     */
    public ChatRoom createChatRoom(String name, String itineraryId,List<String> users) {
        ChatRoom chatRoom = ChatRoom.create(name, itineraryId,users);
        opsHashChatRoom.put(CHAT_ROOMS, chatRoom.getRoomId(), chatRoom);
        return chatRoom;
    }

    public ChatRoom addUserToChatRoom(String roomId, String user) {
        // 기존 채팅방을 검색
        ChatRoom chatRoom = opsHashChatRoom.get(CHAT_ROOMS, roomId);
        if (chatRoom != null) {
            chatRoom.addUser(user);
            opsHashChatRoom.put(CHAT_ROOMS, roomId, chatRoom);
        }
        return chatRoom;
    }

    /**
     * 채팅방 입장 : redis에 topic을 만들고 pub/sub 통신을 하기 위해 리스너를 설정한다.
     */
    public void enterChatRoom(String roomId) {
        ChannelTopic topic = topics.get(roomId);
        if (topic == null)
            topic = new ChannelTopic(roomId);
        redisMessageListener.addMessageListener(redisSubscriber, topic);
        topics.put(roomId, topic);
    }

    public ChannelTopic getTopic(String roomId) {
        return topics.get(roomId);
    }

    public void deleteChatRoom(String roomId) {
        opsHashChatRoom.delete(CHAT_ROOMS, roomId);
    }

    public void deleteAll(){
        redisTemplate.delete(CHAT_ROOMS);
    }

}
