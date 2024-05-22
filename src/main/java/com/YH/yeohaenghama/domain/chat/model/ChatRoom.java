package com.YH.yeohaenghama.domain.chat.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ChatRoom implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;

    private String roomId;
    private String name;
    private List<String> users;

    public static ChatRoom create(String name, String itineraryId, List<String> users) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = itineraryId;
        chatRoom.name = name;
        chatRoom.users = users;
        return chatRoom;
    }

    public void addUser(String user) {
        if (this.users == null) {
            this.users = new ArrayList<>();
        }
        this.users.add(user);
    }
}
