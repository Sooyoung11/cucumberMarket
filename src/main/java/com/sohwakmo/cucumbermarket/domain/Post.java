package com.sohwakmo.cucumbermarket.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Entity(name = "POST")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString(exclude = {"member"})
@SequenceGenerator(name = "POST_SEQ_GEN",sequenceName = "POST_SEQ", allocationSize = 1)
public class Post extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POSTS_SEQ_GEN")
    private Integer postNo; // Primary Key(고유키)

    @NotNull
    @Size(min = 2,max = 30,message = "제목은 2자 이상 30자 이하입니다")
    @Column(nullable = false, unique = true)
    private String title;

    @NotNull
    @Size(min = 2,max = 300,message = "내용은 2자 이상 300자 이하입니다")
    @Column(nullable = false, unique = true)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY) // 다대일 관계(relation)
    private Member member;

    private Integer clickCount;

    private String imageUrl01;

    private String imageUrl02;

    public Post update(String title, String content) {
        this.title = title;
        this.content = content;
        return this;
    }
}
