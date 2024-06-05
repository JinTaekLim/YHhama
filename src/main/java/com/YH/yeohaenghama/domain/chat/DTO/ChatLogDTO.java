package com.YH.yeohaenghama.domain.chat.DTO;


import com.YH.yeohaenghama.domain.chat.model.ChatLog;
import com.YH.yeohaenghama.domain.chat.model.ChatMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ChatLogDTO {
    @Data @Schema(name = "ChatLogDTO_Request")
    public static class Request {
        private String roomId;
        private String sender;
        private String message;
    }

    @Data @Schema(name = "ChatLogDTO_Response")
    public static class Response implements Serializable {
        private String sender;
        private String message;
        private LocalDateTime dateTime;

    }
}
