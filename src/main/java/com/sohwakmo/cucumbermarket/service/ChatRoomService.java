package com.sohwakmo.cucumbermarket.service;

import com.sohwakmo.cucumbermarket.domain.ChatRoom;
import com.sohwakmo.cucumbermarket.domain.Member;
import com.sohwakmo.cucumbermarket.repository.ChatRoomRepository;
import com.sohwakmo.cucumbermarket.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    public List<ChatRoom> getAllChatList(String memberId) {
        Member member = memberRepository.findByMemberId(memberId);
        log.info(member.toString());
        List<ChatRoom> list = chatRoomRepository.findByMember(member);
        log.info("list = {}", list);
        return list;
    }

    public ChatRoom getRoomByRoomId(String roomId) {
        return chatRoomRepository.findById(roomId).get();
    }
}
