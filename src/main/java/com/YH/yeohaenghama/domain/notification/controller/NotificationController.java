package com.YH.yeohaenghama.domain.notification.controller;

import com.YH.yeohaenghama.domain.notification.dto.NotificationShowDTO;
import com.YH.yeohaenghama.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping(value = "/subscribe/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@PathVariable Long id) throws Exception {
        return notificationService.subscribe(id);
    }

    @PostMapping("/subscribe/json")
    public NotificationShowDTO.Response sendData(@RequestParam Long accountId) throws Exception {
        try {
            return notificationService.subscribeJson(accountId);
        } catch (Exception e){
            return null;
        }
    }

    @PostMapping("/send-data/{id}")
    public void sendData(@PathVariable Long id,@RequestParam String event,@RequestParam String data) throws Exception {
        notificationService.sendToClient(id, event, data);
    }





    @DeleteMapping("/delete")
    public void deleteEvent(@RequestParam Long id) {
        notificationService.deleteNotification(id);
    }
}

