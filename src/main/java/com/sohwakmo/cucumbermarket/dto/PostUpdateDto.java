package com.sohwakmo.cucumbermarket.dto;

import com.sohwakmo.cucumbermarket.domain.Member;
import com.sohwakmo.cucumbermarket.domain.Post;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostUpdateDto {
    private Integer postNo;
    private String title;
    private String content;



    public Post toEntity() {
        return Post.builder()
                .title(title).content(content).postNo(postNo).build();
    }
}
