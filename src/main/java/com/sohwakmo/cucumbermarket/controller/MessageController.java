package com.sohwakmo.cucumbermarket.controller;

import com.sohwakmo.cucumbermarket.domain.Message;
import com.sohwakmo.cucumbermarket.repository.MessageRepository;
import com.sohwakmo.cucumbermarket.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final ChatRoomService chatRoomService;
    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달

    //Client가 SEND할 수 있는 경로
    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    //"/pub/chat/enter"
    @MessageMapping(value = "/chat/enter")
    public void enter(Message message){

        message.setMessage("야무지게 물건을 팔아봐요!~!");
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    @MessageMapping(value = "/chat/message")
    public void message(Message message){
        message.setRoomId(message.getRoomId());
        message.setWriter(message.getWriter());
        message.setSendTime(message.getSendTime());
        message.setMessage(message.getMessage());
        chatRoomService.saveMessage(message);
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}
