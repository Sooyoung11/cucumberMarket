package com.sohwakmo.cucumbermarket.dto;

import com.sohwakmo.cucumbermarket.domain.Member;
import com.sohwakmo.cucumbermarket.domain.Post;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostCreateDto {
    private String title;
    private String content;
    private Member member;



    public Post toEntity(){
        return Post.builder()
                .title(title).content(content).member(member).build();
    }
}
