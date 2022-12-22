package com.sohwakmo.cucumbermarket.service;

import com.sohwakmo.cucumbermarket.domain.Member;
import com.sohwakmo.cucumbermarket.domain.Post;
import com.sohwakmo.cucumbermarket.domain.Reply;
import com.sohwakmo.cucumbermarket.domain.ReplyOfLike;
import com.sohwakmo.cucumbermarket.dto.ReplyOfLikeDto;
import com.sohwakmo.cucumbermarket.dto.ReplyReadDto;
import com.sohwakmo.cucumbermarket.dto.ReplyRegisterDto;
import com.sohwakmo.cucumbermarket.dto.ReplyUpdateDto;
import com.sohwakmo.cucumbermarket.repository.MemberRepository;
import com.sohwakmo.cucumbermarket.repository.PostRepository;
import com.sohwakmo.cucumbermarket.repository.ReplyOfLikeRepository;
import com.sohwakmo.cucumbermarket.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;
    private final ReplyOfLikeRepository replyOfLikeRepository;

    public Integer create(ReplyRegisterDto dto) { // 댓글 create 기능
        log.info("dto={}", dto);

        Post post = postRepository.findById(dto.getPostNo()).get();
        Reply reply = Reply.builder().post(post).replyContent(dto.getReplyContent()).replier(dto.getReplier()).secretReply(dto.isSecretReply()).userURL(dto.getUserURL()).likeCount(dto.getLikeCount()).parentReplyNo(dto.getParentReplyNo()).parent(dto.getParent()).build();
        reply = replyRepository.save(reply);

        return  reply.getReplyNo();
    }
    @Transactional(readOnly = true)
    public List<ReplyReadDto> selectByid(Integer postNo, Integer parent) { // 댓글 list 보기 기능
        log.info("selectById=(postNo={},parent={})", postNo, parent);

        List<Reply> list = replyRepository.findByPostPostNoAndParentOrderByReplyNoDesc(postNo, parent);

        return list.stream().map(ReplyReadDto::fromEntity).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ReplyReadDto> selectByReplyNo(Integer replyNo, Integer parent) { // 대댓글 list 보기 기능
        log.info("selectByReplyNo=(replyNo={},parent={})", replyNo, parent);

        List<Reply> list = replyRepository.findByParentReplyNoAndParent(replyNo, parent);
        log.info("list3={}", list);

        return list.stream().map(ReplyReadDto::fromEntity).collect(Collectors.toList());
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

    @Transactional
    public Integer updateLike(ReplyOfLikeDto dto) { // 댓글 좋아요 기능

        Reply reply = replyRepository.findById(dto.getReplyNo()).get();
        log.info("reply={}",reply);
        String replier = dto.getReplier();
        Integer replyNo = reply.getReplyNo();
        log.info("getReplyNo={}",replyNo);

        ReplyOfLike replyOfLike = ReplyOfLike.builder().replyNo(replyNo).replier(dto.getReplier()).build();

        log.info("replyNo={},replier={}",replyNo, replier);
        Optional<ReplyOfLike> result = replyOfLikeRepository.findByReplyNoAndReplier(replyNo, replier);

        log.info("resultList6={}",result.isPresent());
        if(result.isPresent()) {
            Integer a = reply.getLikeCount() - 1;
            reply = reply.likeCount(a);
            replyOfLikeRepository.deleteById(result.get().getNo());

        } else {
            Integer a = reply.getLikeCount() + 1;
            reply = reply.likeCount(a);
            replyOfLikeRepository.save(replyOfLike);
        }

        return 0;
    }
}