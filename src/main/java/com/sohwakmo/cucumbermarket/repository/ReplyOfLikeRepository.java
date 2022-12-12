package com.sohwakmo.cucumbermarket.repository;

import com.sohwakmo.cucumbermarket.domain.Reply;
import com.sohwakmo.cucumbermarket.domain.ReplyOfLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReplyOfLikeRepository extends JpaRepository<ReplyOfLike, Integer> {
    Optional<ReplyOfLike> findByReplyNoAndReplier(Integer replyNo, String replier); // select
}
