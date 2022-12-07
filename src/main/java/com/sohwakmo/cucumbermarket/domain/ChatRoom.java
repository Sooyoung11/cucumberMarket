package com.sohwakmo.cucumbermarket.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class ChatRoom {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private String roomId;

    private Message message;
}
