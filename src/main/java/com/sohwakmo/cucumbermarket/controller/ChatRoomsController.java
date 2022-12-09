package com.sohwakmo.cucumbermarket.controller;

import com.sohwakmo.cucumbermarket.domain.ChatRoom;
import com.sohwakmo.cucumbermarket.domain.Member;
import com.sohwakmo.cucumbermarket.domain.Message;
import com.sohwakmo.cucumbermarket.repository.MemberRepository;
import com.sohwakmo.cucumbermarket.repository.MessageRepository;
import com.sohwakmo.cucumbermarket.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatRoomsController {
    private final ChatRoomService chatRoomService;
    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;

    @GetMapping
    public String gotoChatList(){
        return "/chat/chat";
    }
    @GetMapping("/list")
    public String showChatList(Integer memberNo, Model model){
        log.info("memberId={}",memberNo);
        List<ChatRoom> list = chatRoomService.getAllChatList(memberNo);
        String memberNickname = chatRoomService.getLoginedName(memberNo);
        model.addAttribute("list",list);
        model.addAttribute("memberNo",memberNo);
        model.addAttribute("nickName", memberNickname);
        return "/chat/chatList";
    }


    @GetMapping("/chatRoom")
    public void getRoom(String roomId,String nickname,Integer memberNo,Model model){
        ChatRoom chatRoom = chatRoomService.getRoomByRoomId(roomId,memberNo,nickname);
        List<Message> loadMessage = chatRoomService.getAllMessages(roomId,nickname);
        log.info(chatRoom.toString());
        Member member = memberRepository.findByNickname(nickname).orElse(null);
        Integer nicknameNum = member.getMemberNo();
        model.addAttribute("room", chatRoom);
        model.addAttribute("nickname",nickname);
        model.addAttribute("nicknameNum",nicknameNum);
        model.addAttribute("messages", loadMessage);
    }
}
