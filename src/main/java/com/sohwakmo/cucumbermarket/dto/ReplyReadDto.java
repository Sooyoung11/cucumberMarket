package com.sohwakmo.cucumbermarket.dto;

import com.sohwakmo.cucumbermarket.domain.Reply;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class ReplyReadDto {
    private Integer replyNo; // 댓글 번호
    private Integer postNo; // 댓글이 달린 포스트 번호
    private String replyContent; // 댓글 내용
    private String replier; // 댓글 작성자
    private boolean secretReply; // 비밀 댓글 여부
    private Integer parent; // 댓글 구분
    private String createdTime; // 댓글 최초 작성 시간
    private String modifiedTime; // 댓글 최종 수정 시간
    private Integer likeCount; // 댓글 좋아요


    // Entity 객체에서 DTO 객체를 생성해서 리턴하는 메서드replyRepository
    public static ReplyReadDto fromEntity(Reply entity) {
        ReplyReadDto build = ReplyReadDto.builder()
                .replyNo(entity.getReplyNo()).postNo(entity.getPost().getPostNo()).replyContent(entity.getReplyContent()).replier(entity.getReplier()).parent(entity.getParent())
                .createdTime(formatDate(entity.getCreatedTime())).modifiedTime(formatDate(entity.getModifiedTime())).secretReply(entity.isSecretReply()).replyNo(entity.getReplyNo()).likeCount(entity.getLikeCount())
                .build();
        return build;
    }

    public static String formatDate(LocalDateTime time){
        String formatDate = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        return formatDate;
    }


}