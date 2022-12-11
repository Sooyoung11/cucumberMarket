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

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private  final MessageRepository messageRepository;
    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    public List<ChatRoom> getAllChatList(Integer memberNo) {
        Member member = memberRepository.findById(memberNo).get(); // 차은우가담김
        log.info(member.toString());
        List<ChatRoom> list = chatRoomRepository.findByMemberOrRoomId(member,member.getNickname()); // 차은우랑, 차은우가 담김
        log.info("list = {}", list);
        return list;
    }

    @Transactional
    public ChatRoom getRoomByRoomId(String roomId, Integer memberNo, String nickname) {
        List<ChatRoom> chatRoom = chatRoomRepository.findByRoomIdOrMemberMemberNo(roomId,memberNo);
        Member member = memberRepository.findById(memberNo).orElse(null);
        Member member1 = memberRepository.findByNickname(nickname).orElse(null);
        for(ChatRoom c : chatRoom){
            if(c.getMember().getMemberNo().equals(member1.getMemberNo())) {
                return c;
            }
        }
        ChatRoom c = new ChatRoom(roomId,member1);
        chatRoomRepository.save(c);
        return c;
    }

    @Transactional
    public void saveMessage(Message message) {
        Message message1 = messageRepository.save(message);
        log.info(message1.toString());
    }

    @Transactional
    public List<Message> getAllMessages(String roomId, String nickname) {
        Member member = memberRepository.findByNickname(nickname).get();
        List<Message> message = messageRepository.findByRoomIdAndMessageNumOrderById(roomId,member.getMemberNo());
        if(message==null){
            Message newMessage = new Message();
            newMessage.setRoomId(roomId);
            messageRepository.save(newMessage);
            log.info("massageSave={}",newMessage);
        }
        List<Message> messageList = messageRepository.findByRoomIdAndMessageNumOrderById(roomId, member.getMemberNo()).stream().toList();
        for(Message m : messageList){
            String sendTime = m.getSendTime();
            if (sendTime.length() == 24) {
                sendTime = sendTime.substring(sendTime.length() - 10, sendTime.length() - 3);
                m.setSendTime(sendTime);
            }else if(sendTime.length()==25){
                sendTime = sendTime.substring(sendTime.length() - 11, sendTime.length() - 3);
                m.setSendTime(sendTime);
            }
        }
        log.info("messageList={}",messageList);
        return messageList;
    }

    public ChatRoom setUser(String roomId) {
        Member member = memberRepository.findByNickname(roomId).orElse(null);
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElse(null);
        chatRoom.setMember(member);
        return chatRoom;
    }

    public String getLoginedName(Integer memberNo) {
        Member member = memberRepository.findById(memberNo).orElse(null);
        return member.getNickname();
    }
}
