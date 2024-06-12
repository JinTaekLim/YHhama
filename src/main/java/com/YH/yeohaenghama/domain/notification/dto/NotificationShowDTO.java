package com.YH.yeohaenghama.domain.notification.dto;

import com.YH.yeohaenghama.domain.notification.entity.Notification;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NotificationShowDTO {
    public Long id;
    public String event;
    public String data;

    @Schema(name = "NotificationShowDTO_Response") @Data
    public static class Response{
        public List<NotificationShowDTO> notification;

        public static NotificationShowDTO.Response toEntity(List<Notification> notificationList) {
            NotificationShowDTO.Response response = new Response();
            List<NotificationShowDTO> notificationResponse = new ArrayList<>();

            for (Notification notification : notificationList) {

                NotificationShowDTO notificationShowDTO = new NotificationShowDTO();

                notificationShowDTO.setId(notification.getId());
                notificationShowDTO.setEvent(notification.getEvent());
                notificationShowDTO.setData(notification.getData());

                notificationResponse.add(notificationShowDTO);
            }

            response.setNotification(notificationResponse);

            return response;
        }
    }
}
