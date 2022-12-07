package com.sohwakmo.cucumbermarket.domain;

import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
@Getter
@EntityListeners(value = {AuditingEntityListener.class})

public class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime createdTime;

    @LastModifiedDate
    private LocalDateTime modifiedTime;
}
