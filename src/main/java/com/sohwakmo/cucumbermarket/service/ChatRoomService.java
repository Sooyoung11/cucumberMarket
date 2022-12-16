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
import java.util.Collections;
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
        List<ChatRoom> list = chatRoomRepository.findByRoomIdOrMemberMemberNo(member.getNickname(),memberNo);// 차은우랑, 차은우가 담김
        log.info("list = {}", list);
        return list;
    }

    @Transactional
    public ChatRoom getRoomByRoomId(String roomId, Integer memberNo, String nickname) {
        List<ChatRoom> chatRoom = chatRoomRepository.findByRoomIdOrMemberMemberNo(roomId,memberNo);
        Member member = memberRepository.findByNickname(nickname).orElse(null);
        for(ChatRoom c : chatRoom){
            if(c.getMember().getMemberNo().equals(member.getMemberNo())&&c.getRoomId().equals(roomId)) {
                if(c.getLastEnterName().equals(c.getLeavedUser())){ // 한번 채팅방을 나갔다가 다시 그 사람에게 메세지를 보내서 채팅리스트에 띄워져야하는경우
                    c.setLeavedUser("nobody");
                }
                return c;
            }
        }
        ChatRoom c = new ChatRoom(roomId,member,"nobody",0);
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

    public String getLoginedName(Integer memberNo) {
        Member member = memberRepository.findById(memberNo).orElse(null);
        return member.getNickname();
    }

    /**
     * 가장 최근에 보낸 메세지를 가져온다. desc 정렬로 가장 위에 있는 메세지가 가장 최근에 보낸 메세지.
     * @param memberNo
     * @return 가장 최근에 보낸 메세지를 리턴한다.
     */
    public String getRecentMessage(String roomId,Integer memberNo) {
        List<Message> messages = messageRepository.findByMessageNumAndRoomIdOrderByIdDesc(memberNo,roomId);
        if(messages.size()!=0){
            return String.valueOf(messages.get(0).getMessage());
        }else{
            return null;
        }
    }

    @Transactional
    /**
     * 채팅방에 누가 제일 마지막에 들어갔는지 세팅한다.
     * @param roomId 채팅방이름
     * @param nickname 컬럼에 채워질 이름
     */
    public void setLastCheckUser(String roomId, String nickname, Integer memberNo) {
        Member member = memberRepository.findByNickname(nickname).get();
        Member lastEnterMember = memberRepository.findById(memberNo).get(); // 마지막으로 들어간 사람의 이름을 얻는다
        ChatRoom chatRoom = chatRoomRepository.findByRoomIdAndMemberMemberNo(roomId, member.getMemberNo());
        chatRoom.setLastEnterName(lastEnterMember.getNickname());
    }

    /**
     * 안읽은 메세지를 반복문을 돌려서 리턴해준다
     * @return 안읽은 메세지를 1씩더해가면서 있으면 그 값리턴, 없으면 0 리턴.
     */
    public Integer checkUnReadMessages(String roomId,Integer memberNo, String memberNickname,String lastEnterName) {
        List<Message>messages = messageRepository.findByMessageNumAndRoomIdOrderByIdDesc(memberNo,roomId);
        int unReadMessage = 0;
        for(Message m : messages){
           if(m.getWriter().equals(memberNickname)){ // 마지막에 작성한 사람이 나인경우
               break;
           }else{ // 마지막에 작성한 사람이 내가 아닐경우
               if(lastEnterName.equals(memberNickname)){ // 마지막에 작성한 사람이 내가 아닐경우에 읽씹이 가능하므로 마지막에 들어간사람이 나라면 0리턴
                   break;
               }else { // 마지막으로 채팅방에 들어간게 나도 아니고, 마지막 작성자가 상대일경우 이제 카운트 시작.
                   unReadMessage++;
               }
           }
        }
        return unReadMessage;
    }

    @Transactional
    public void deleteChatRoom(String roomId, String nickname,Integer memberNo) {
        Member member = memberRepository.findByNickname(nickname).orElse(null);
        Member loginUser = memberRepository.findById(memberNo).orElse(null);
        ChatRoom chatRoom = chatRoomRepository.findByRoomIdAndMemberMemberNo(roomId, member.getMemberNo());

        // TODO 여기서 if문으로 묶어서 2명이된순간 해야함.
        if(!chatRoom.getLeavedUser().equals("nobody")){ // nobody가 아니면 즉, 한명이라도 채팅방을 나가면 이제 채팅방을 삭제한다.
            chatRoomRepository.delete(chatRoom);
            List<Message> deleteMessageList1 = messageRepository.findByMessageNumAndRoomIdOrderByIdDesc(loginUser.getMemberNo(), roomId);
//            Member deleteMessageMember = memberRepository.findByNickname(roomId).get();
            List<Message> deleteMessageList2 = messageRepository.findByMessageNumAndRoomIdOrderByIdDesc(member.getMemberNo(), roomId);
            messageRepository.deleteAllInBatch(deleteMessageList1);
            messageRepository.deleteAllInBatch(deleteMessageList2);
        }else{
            chatRoom.setLeavedUser(loginUser.getNickname());
        }
    }

    /**
     * 현재 로그인 되어있는 유저의 정보를 회원번호로 가져온다.
     * @param memberNo 회원번호
     * @return 로그인 되어있는 유저의 객체
     */
    public Member getLoginedMember(Integer memberNo) {
        return memberRepository.findById(memberNo).get();
    }

    @Transactional
    public void setMessages(ChatRoom c,String memberNickname) {
        c.setMessage(getRecentMessage(c.getRoomId(),c.getMember().getMemberNo()));
        c.setUnReadMessages(checkUnReadMessages(c.getRoomId(),c.getMember().getMemberNo(),memberNickname, c.getLastEnterName()));
    }
}
