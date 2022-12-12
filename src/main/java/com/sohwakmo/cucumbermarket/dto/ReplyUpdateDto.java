package com.sohwakmo.cucumbermarket.dto;


import com.sohwakmo.cucumbermarket.domain.Reply;
import lombok.Data;

@Data
public class ReplyUpdateDto {

    private Integer replyNo;
    private String replyContent;
    private Integer likeCount;

    public Reply toEntity() {
        return Reply.builder().replyNo(replyNo).replyContent(replyContent).build();
    }

    public Reply toEntityByCount() {
        return Reply.builder().replyNo(replyNo).build();
    }

}