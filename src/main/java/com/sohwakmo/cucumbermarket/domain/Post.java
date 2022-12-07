package com.sohwakmo.cucumbermarket.domain;


import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "POST")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString(exclude = {"member"})
@SequenceGenerator(name = "POST_SEQ_GEN",sequenceName = "POST_SEQ", allocationSize = 1)
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POSTS_SEQ_GEN")
    private Integer postNo; // Primary Key(고유키)

    @Column(nullable = false)
    private String title;


    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY) // 다대일 관계(relation)
    private Member member;


    @ColumnDefault("0")
    private Integer clickCount;

    private String imageUrl01;

    private String imageName01;

    private String imageUrl02;

    private String imageName02;

    public Post update(String title, String content) {
        this.title = title;
        this.content = content;
        return this;
    }

    public Post plusClickCount(Integer clickCount){
        this.clickCount = clickCount;
        return this;
    }
}
