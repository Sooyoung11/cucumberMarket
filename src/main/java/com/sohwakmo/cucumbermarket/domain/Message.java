package com.sohwakmo.cucumbermarket.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@SequenceGenerator(name = "POST_SEQ_GEN",sequenceName = "MESSAGE_SEQ", allocationSize = 1)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MESSAGE_SEQ_GEN")
    private long id;
    private String message;
    private String writer;
    private String receiver;
    private LocalDateTime sendTime;
    private String roomId;

    public Message update(String roomId) {
        this.roomId = roomId;
        return this;
    }
}
