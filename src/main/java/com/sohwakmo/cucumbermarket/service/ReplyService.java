package com.sohwakmo.cucumbermarket.service;

import com.sohwakmo.cucumbermarket.domain.Post;
import com.sohwakmo.cucumbermarket.domain.Reply;
import com.sohwakmo.cucumbermarket.dto.ReplyReadDto;
import com.sohwakmo.cucumbermarket.dto.ReplyRegisterDto;
import com.sohwakmo.cucumbermarket.dto.ReplyUpdateDto;
import com.sohwakmo.cucumbermarket.repository.PostRepository;
import com.sohwakmo.cucumbermarket.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;

    public Integer create(ReplyRegisterDto dto) { // 댓글 create 기능
        log.info("dto={}", dto);

        Post post = postRepository.findById(dto.getPostNo()).get();
        Reply reply = Reply.builder().post(post).replyContent(dto.getReplyContent()).replyer(dto.getReplier()).build();
        reply = replyRepository.save(reply);

        return  reply.getReplyNo();
    }
    @Transactional(readOnly = true) 
    public List<ReplyReadDto> selectByid(Integer postNo) { // 댓글 list 보기 기능
        log.info("selectById=(postNo={})", postNo);

        List<Reply> list = replyRepository.findByPostPostNoOrderByReplyNoDesc(postNo);

        return list.stream().map(ReplyReadDto::fromEntity).toList();
    }

    @Transactional(readOnly = true) // detail 화면 보기 기능
    public ReplyReadDto readReply(Integer replyNo) {
        log.info("readReply(replyId={})", replyNo);

        Reply reply = replyRepository.findById(replyNo).get();

        return ReplyReadDto.fromEntity(reply);
    }

    public Integer delete(Integer replyNo) { // 댓글 delete 기능
        log.info("deleteReply(replyNo={})", replyNo);

        replyRepository.deleteById(replyNo);

        return replyNo;
    }

    @Transactional
    public Integer update(ReplyUpdateDto dto) { // 댓글 update 기능
        log.info("updateReply(dto={})", dto);

        Reply reply =  replyRepository.findById(dto.getReplyNo()).get();
        reply.update(dto.getReplyContent());
        // @Transactional이 적용된 경우에 메서드 실행이 끝날 때 DB에 자동으로 save됨.

        return reply.getReplyNo();
    }
}
