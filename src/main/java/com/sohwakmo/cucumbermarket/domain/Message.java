package com.sohwakmo.cucumbermarket.domain;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@SequenceGenerator(name = "MESSAGE_SEQ_GEN",sequenceName = "MESSAGE_SEQ", allocationSize = 1)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MESSAGE_SEQ_GEN")
    private long id;
    private String message;
    private String writer;
    private String sendTime;
    private Integer messageNum;
    private String roomId;

    public Message update(String roomId) {
        this.roomId = roomId;
        return this;
    }
}
