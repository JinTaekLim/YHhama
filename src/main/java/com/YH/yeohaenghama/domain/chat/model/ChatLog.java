package com.YH.yeohaenghama.domain.chat.model;

import com.YH.yeohaenghama.domain.chat.DTO.ChatLogDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ChatLog implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;

    private String roomId;
    private List<ChatLogDTO.Response> messages;

    public ChatLog(String roomId) {
        this.roomId = roomId;
        this.messages = new ArrayList<>();
    }
}
