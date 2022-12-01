package com.sohwakmo.cucumbermarket.dto;

import lombok.Data;

@Data
public class ReplyRegisterDto {

    // 필드 이름들은 Ajax 요청에서 사용된 data 객체의 속성 이름과 동일
    private Integer postNo;
    private String replyContent;
    private String replier;
    private boolean secretReply;
}
