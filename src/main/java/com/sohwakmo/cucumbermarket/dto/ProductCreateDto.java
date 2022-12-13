package com.sohwakmo.cucumbermarket.dto;

import com.sohwakmo.cucumbermarket.domain.Member;
import com.sohwakmo.cucumbermarket.domain.Product;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Builder
@Data
public class ProductCreateDto {
    private Member member;
    private String title;
    private String content;
    private Integer price;
    private String category;
    private Integer clickCount;
    private Integer likeCount;

    private List<MultipartFile> photoList;

    public Product toEntity() {
        return Product.builder()
                .member(member)
                .title(title)
                .content(content)
                .price(price)
                .category(category)
                .clickCount(clickCount)
                .likeCount(likeCount)
                .build();
    }

}
