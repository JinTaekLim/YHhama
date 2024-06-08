package com.YH.yeohaenghama.domain.notification.repository;

import com.YH.yeohaenghama.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByAccountId(Long accountId);
}