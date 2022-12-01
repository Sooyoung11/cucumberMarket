package com.sohwakmo.cucumbermarket.dto;

import com.sohwakmo.cucumbermarket.domain.Post;
import lombok.Data;

@Data
public class PostUpdateDto {
    private Integer id;
    private String title;
    private String content;

    public Post toEntity() {
        return Post.builder()
                .title(title).content(content).postNo(id).build();
    }
}
