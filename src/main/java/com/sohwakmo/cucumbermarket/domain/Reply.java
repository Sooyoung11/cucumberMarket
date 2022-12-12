package com.sohwakmo.cucumbermarket.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString(exclude = {"post"}) // 필드 post, member는 toString()에서 제외
@Entity(name = "REPLY") // 테이블명
@SequenceGenerator(name = "REPLY_SEQ_GEN", sequenceName = "REPLY_SEQ", allocationSize = 1)
public class Reply extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REPLY_SEQ_GEN")
    private Integer replyNo; // Reply 고유키

    @ManyToOne(fetch = FetchType.LAZY) // 다대일 관계
    private Post post;

    @Column(nullable = false, length = 1000)
    private String replyContent; // reply 내용

    @Column(nullable = false)
    private String replier; // reply 작성자

    private boolean secretReply; // 비밀 답글

    @ColumnDefault("0")
    private Integer likeCount; // 댓글 좋아요

    @Column
    private Integer parent; // 댓글, 대댓글 구분

    @ColumnDefault("0") // 댓글은 0, 대댓글은 댓글의 번호
    private Integer parentReplyNo; // 대댓글의 댓글 번호 구분

    public Reply update(String replyContent) {
        this.replyContent = replyContent;
        return this;
    }
    public Reply likeCount(Integer likeCount){
        this.likeCount = likeCount;
        return this;
    }

}