package com.sohwakmo.cucumbermarket.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Message {

    @Id
    private String roomId;

    private String writer;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChatRoom chatRoom;

    private String message;

    private String sendMessage;

    private String receivedMessage;

    @CreationTimestamp
    private LocalDateTime sendTime;
}
