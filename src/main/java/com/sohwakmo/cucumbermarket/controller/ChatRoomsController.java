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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String showChatList(Integer memberNo,Model model){ // 멤버넘버로 닉네임도 찾아서 채팅방리스트에서 메세지 리시트도 받아야함.
        log.info("memberId={}",memberNo);
        List<ChatRoom> list = chatRoomService.getAllChatList(memberNo);
        String memberNickname = chatRoomService.getLoginedName(memberNo);
        Member member = chatRoomService.getLoginedMember(memberNo);
        for(ChatRoom c : list){
            if(c.getLeavedUser().equals(member.getNickname())){
                list.remove(c); // 나간 채팅방 사람과 지금 로그인한 사람의 이름이 같으면 채팅방 리스트에서 제거
                if(list.size()==0){ // 채팅방 리스트의 사이즈가 0이면 반복문 종료 -> 안하면 NullPointerException 발생!
                    break;
                }
                continue; // 더 채팅방이 남아있다면 밑의 메서드실행을 위해서 반복문으로 다시 이동
            }
            c.setMessage(chatRoomService.getRecentMessage(c.getRoomId(),c.getMember().getMemberNo()));
            c.setUnReadMessages(chatRoomService.checkUnReadMessages(c.getRoomId(),c.getMember().getMemberNo(),memberNickname, c.getLastEnterName()));
        }
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
        chatRoomService.setLastCheckUser(roomId,nickname,memberNo); // 이 채팅방에 누가 제일 마직막에 들어갔는지 업데이트
        model.addAttribute("room", chatRoom);
        model.addAttribute("nickname",nickname);
        model.addAttribute("memberNo", memberNo);
        model.addAttribute("nicknameNum",nicknameNum);
        model.addAttribute("messages", loadMessage);
    }

    @GetMapping("/delete")
    public String deleteChatRoom(String roomId, String nickname, Integer memberNo, RedirectAttributes attributes){
        chatRoomService.deleteChatRoom(roomId, nickname,memberNo);
        attributes.addAttribute("memberNo", memberNo);
        return "redirect:/chat/list";
    }
}
