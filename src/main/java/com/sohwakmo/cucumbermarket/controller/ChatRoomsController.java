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


    /**
     * 마이페이지에서 채팅 리스트의 목록을 불러온다.
     * @param memberNo 회원 번호를 받아서정보를 가져온다.
     * @param model 
     * @return 채팅리스트
     */
    @GetMapping("/list")
    public String showChatList(Integer memberNo,Model model){ // 멤버넘버로 닉네임도 찾아서 채팅방리스트에서 메세지 리시트도 받아야함
        log.info("memberId={}",memberNo);
        List<ChatRoom> list = chatRoomService.getAllChatList(memberNo);
        String memberNickname = chatRoomService.getLoginedName(memberNo);
        for(ChatRoom c : list){
            c.setMessage(chatRoomService.getRecentMessage(c.getMember().getMemberNo()));
        }
        model.addAttribute("list",list);
        model.addAttribute("memberNo",memberNo);
        model.addAttribute("nickName", memberNickname);
        return "/chat/chatList";
    }


    /**
     * 물품 게시물에서 거래완료로 변경 했을시 나오는 팝업창에서 채팅 리스트를 띄움
     * @param memberNo 유저번호로 채팅방 리스트를 가져온다.
     * @return 채팅방 리스트가 있는 페이지로 이동.
     */
    @GetMapping("/productChatList")
    public void showProductChatList(Integer memberNo,Model model){
        List<ChatRoom> list = chatRoomService.getAllChatList(memberNo);
        String memberNickname = chatRoomService.getLoginedName(memberNo);
        for(ChatRoom c : list){
            c.setMessage(chatRoomService.getRecentMessage(c.getMember().getMemberNo()));
        }
        model.addAttribute("list",list);
        model.addAttribute("memberNo",memberNo);
        model.addAttribute("nickName", memberNickname);
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
        model.addAttribute("memberNo", memberNo);
        model.addAttribute("nicknameNum",nicknameNum);
        model.addAttribute("messages", loadMessage);
    }
}
