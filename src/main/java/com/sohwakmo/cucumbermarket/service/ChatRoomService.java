package com.sohwakmo.cucumbermarket.service;

import com.sohwakmo.cucumbermarket.domain.ChatRoom;
import com.sohwakmo.cucumbermarket.domain.Member;
import com.sohwakmo.cucumbermarket.domain.Message;
import com.sohwakmo.cucumbermarket.repository.ChatRoomRepository;
import com.sohwakmo.cucumbermarket.repository.MemberRepository;
import com.sohwakmo.cucumbermarket.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private  final MessageRepository messageRepository;
    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    public List<ChatRoom> getAllChatList(Integer memberNo) {
        Member member = memberRepository.findById(memberNo).get();
        log.info(member.toString());
        List<ChatRoom> list = chatRoomRepository.findByMember(member);
        log.info("list = {}", list);
        return list;
    }

    public ChatRoom getRoomByRoomId(String roomId) {
        return chatRoomRepository.findById(roomId).get();
    }

    @Transactional
    public void saveMessage(Message message) {
        Message message1 = messageRepository.save(message);
        log.info(message1.toString());
    }

    @Transactional
    public List<Message> getAllMessages(String roomId) {
        List<Message> message = messageRepository.findByRoomId(roomId);
        if(message==null){
            Message newMessage = new Message();
            newMessage.setRoomId(roomId);
            messageRepository.save(newMessage);
            log.info("massageSave={}",newMessage);
        }
        List<Message> messageList = messageRepository.findByRoomId(roomId).stream().toList();
        log.info("messageList={}",messageList);
        return messageList;
    }
}
