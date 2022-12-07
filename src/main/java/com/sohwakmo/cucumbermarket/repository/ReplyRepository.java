package com.sohwakmo.cucumbermarket.repository;

import com.sohwakmo.cucumbermarket.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {


    List<Reply> findByPostPostNoOrderByReplyNoDesc(Integer postNo); // POST 게시물에 따른 댓글 리스트 보여주는 select문
}
