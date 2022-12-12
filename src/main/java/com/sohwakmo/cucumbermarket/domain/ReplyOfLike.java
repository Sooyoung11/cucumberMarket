package com.sohwakmo.cucumbermarket.domain;


import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@Entity(name = "REPLY_OF_LIKE")
@SequenceGenerator(name = "REPLY_OF_LIKE_SEQ_GEN", sequenceName = "REPLY_OF_LIKE_SEQ", allocationSize = 1)
public class ReplyOfLike {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REPLY_OF_LIKE_SEQ_GEN")
    private Integer no; // 고유키

    @Column(nullable = false)
    private String replier; // 작성자

    @Column(nullable = false)
    private Integer replyNo; // 댓글 고유키

}
