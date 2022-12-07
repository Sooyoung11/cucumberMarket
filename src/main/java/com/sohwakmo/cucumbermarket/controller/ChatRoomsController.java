package com.sohwakmo.cucumbermarket.controller;

import com.sohwakmo.cucumbermarket.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String showChatList(String memberId){
        log.info("memberId={}",memberId);


        return "/chat/chatList";
    }
}
