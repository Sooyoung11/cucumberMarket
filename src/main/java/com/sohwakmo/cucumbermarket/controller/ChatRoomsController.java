package com.sohwakmo.cucumbermarket.controller;

import com.sohwakmo.cucumbermarket.domain.ChatRoom;
import com.sohwakmo.cucumbermarket.domain.Message;
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

    @GetMapping
    public String gotoChatList(){
        return "/chat/chat";
    }
    @GetMapping("/list")
    public String showChatList(Integer memberNo, Model model){
        log.info("memberId={}",memberNo);
        // TODO 채팅
        List<ChatRoom> list = chatRoomService.getAllChatList(memberNo);
        model.addAttribute("list",list);
        return "/chat/chatList";
    }


    @GetMapping("/chatRoom")
    public void getRoom(String roomId,Model model){
        ChatRoom chatRoom = chatRoomService.getRoomByRoomId(roomId);
        List<Message> loadMessage = chatRoomService.getAllMessages(roomId);
        log.info(chatRoom.toString());
        model.addAttribute("room", chatRoom);
        model.addAttribute("messages", loadMessage);
    }
}
