package com.sohwakmo.cucumbermarket.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "CHAT_ROOM")
public class ChatRoom {

    @Id
    private String roomId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

//    private Message message;
}
