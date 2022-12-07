package com.sohwakmo.cucumbermarket.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    private String roomId;

    private String message;

    @Column
    private String sendMessage;

    @Column
    private String receivedMessage;

    @CreationTimestamp
    private LocalDateTime sendTime;
}
