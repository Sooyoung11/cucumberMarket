package com.sohwakmo.cucumbermarket.domain;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

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

    private String lastEnterName;


    private Integer unReadMessages;

    private String leavedUser;

    private String userImage;


    public ChatRoom(String roomId, Member member,String leavedUser,Integer unReadMessages) {
        this.roomId = roomId;
        this.member = member;
        this.leavedUser=leavedUser;
        this.unReadMessages = unReadMessages;
    }


}
