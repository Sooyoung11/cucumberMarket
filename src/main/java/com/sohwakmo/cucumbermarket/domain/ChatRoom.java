package com.sohwakmo.cucumbermarket.domain;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@NoArgsConstructor
@Entity(name = "CHAT_ROOM")
@SequenceGenerator(name = "CHATROOM_SEQ_GEN",sequenceName = "CHAT_SEQ", allocationSize = 1)
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CHATROOM_SEQ_GEN")
    private Long id;


    private String roomId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;


    private String message;


    public ChatRoom(String roomId, Member member) {
        this.roomId = roomId;
        this.member = member;
    }
}
