package com.sohwakmo.cucumbermarket.dto;

import com.sohwakmo.cucumbermarket.domain.Member;
import com.sohwakmo.cucumbermarket.domain.Post;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder

public class PostCreateDto {

    @NotNull
    @Size(min = 2,max = 10)
    private String title;
    @NotNull
    @Size(min=2,max = 100)
    private String content;

    private Integer clickCount;
    private Member member;

    public Post toEntity(){
        return Post.builder()
                .title(title).content(content).clickCount(clickCount).member(member).build();
    }
}
