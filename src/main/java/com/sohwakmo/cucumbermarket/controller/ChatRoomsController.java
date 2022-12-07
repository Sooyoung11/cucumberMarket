package com.sohwakmo.cucumbermarket.controller;

import com.sohwakmo.cucumbermarket.domain.ChatRoom;
import com.sohwakmo.cucumbermarket.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
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

    @GetMapping
    public String gotoChatList(){
        return "/chat/chat";
    }
    @GetMapping("/list")
    public String showChatList(String memberId, Model model){
        log.info("memberId={}",memberId);
        List<ChatRoom> list = chatRoomService.getAllChatList(memberId);
        model.addAttribute("list",list);
        return "/chat/chatList";
    }

    @GetMapping("/chatRoom")
    public void getRoom(String roomId,Model model){
        ChatRoom chatRoom = chatRoomService.getRoomByRoomId(roomId);
        log.info(chatRoom.toString());
        model.addAttribute("room", chatRoom);
    }
}
