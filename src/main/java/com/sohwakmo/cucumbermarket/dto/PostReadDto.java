package com.sohwakmo.cucumbermarket.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PostReadDto {
    private Integer postNo;
    private String title;
    private String writer;
    private LocalDateTime createdTime;
    private Integer clickCount;
}
