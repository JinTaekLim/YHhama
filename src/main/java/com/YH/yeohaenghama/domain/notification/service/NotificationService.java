package com.YH.yeohaenghama.domain.notification.service;

import com.YH.yeohaenghama.domain.notification.dto.NotificationShowDTO;
import com.YH.yeohaenghama.domain.notification.entity.Notification;
import com.YH.yeohaenghama.domain.notification.repository.EmitterRepository;
import com.YH.yeohaenghama.domain.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class NotificationService {
    // 기본 타임아웃 설정
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    private final EmitterRepository emitterRepository;

    private final NotificationRepository notificationRepository;

    public SseEmitter subscribe(Long accountId) throws Exception {
        SseEmitter emitter = createEmitter(accountId);

        List<Notification> notificationsList = notificationRepository.findByAccountId(accountId);

        emitter.send(SseEmitter.event().id(null).name("Connect").data("[ accountId=" + accountId + " ]"));
        if(!notificationsList.isEmpty()) {
            for(Notification n : notificationsList){
                emitter.send(SseEmitter.event().id(String.valueOf(n.getId())).name(n.getEvent()).data(n.getData()));
            }
        }
        return emitter;
    }

    public NotificationShowDTO.Response subscribeJson(Long accountId){
        List<Notification> notificationsList = notificationRepository.findByAccountId(accountId);
        if(notificationsList.isEmpty()) throw new NoSuchElementException("알람이 존재하지 않습니다.");
        return NotificationShowDTO.Response.toEntity(notificationsList);
    }


    public void sendToClient(Long accountId, String event, String data) throws Exception {
        SseEmitter emitter = emitterRepository.get(accountId);
        if (emitter != null) {
            emitter.send(SseEmitter.event().id(String.valueOf(saveNotification(accountId,event,data).getId())).name(event).data(data));
        }
    }



    private Notification saveNotification(Long accountId, String event, String data){
        Notification notification = Notification.builder()
                .accountId(accountId)
                .event(event)
                .data(data)
                .build();


        return notificationRepository.save(notification);
    }

    public void deleteNotification(Long id){
        notificationRepository.deleteById(id);
    }



    private SseEmitter createEmitter(Long id) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(id, emitter);

        // Emitter가 완료될 때(모든 데이터가 성공적으로 전송된 상태) Emitter를 삭제한다.
        emitter.onCompletion(() -> emitterRepository.deleteById(id));
        // Emitter가 타임아웃 되었을 때(지정된 시간동안 어떠한 이벤트도 전송되지 않았을 때) Emitter를 삭제한다.
        emitter.onTimeout(() -> emitterRepository.deleteById(id));

        return emitter;
    }

}