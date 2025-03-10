package com.YH.yeohaenghama.common;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners({AuditingEntityListener.class})
@Getter
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    protected LocalDateTime createdAt;

    @LastModifiedBy
    @Column(nullable = true)
    protected LocalDateTime updatedAt;
}