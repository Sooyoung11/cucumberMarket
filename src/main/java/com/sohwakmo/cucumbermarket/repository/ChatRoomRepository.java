package com.sohwakmo.cucumbermarket.repository;

import com.sohwakmo.cucumbermarket.domain.ChatRoom;
import com.sohwakmo.cucumbermarket.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,String> {
    List<ChatRoom> findByMemberOrRoomId(Member member,String roomId);
    List<ChatRoom> findByRoomIdOrMemberMemberNo(String roomId, Integer memberNo);

    ChatRoom findByRoomIdAndMemberMemberNo(String roomId, Integer memberNo);
}
