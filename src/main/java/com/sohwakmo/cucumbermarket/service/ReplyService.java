package com.sohwakmo.cucumbermarket.service;

import com.sohwakmo.cucumbermarket.domain.Post;
import com.sohwakmo.cucumbermarket.domain.Reply;
import com.sohwakmo.cucumbermarket.dto.ReplyRegisterDto;
import com.sohwakmo.cucumbermarket.repository.PostRepository;
import com.sohwakmo.cucumbermarket.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;
    public Integer create(ReplyRegisterDto dto) {
        Post post = postRepository.findById(dto.getPostNo()).get();
        Reply reply = Reply.builder().post(post).replyContent(dto.getReplyContent()).replyer(dto.getReplyer()).build();
        reply = replyRepository.save(reply);
        return  reply.getReplyNo();
    }
}
