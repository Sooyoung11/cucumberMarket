package com.sohwakmo.cucumbermarket.dto;

import com.sohwakmo.cucumbermarket.domain.Product;
import lombok.Data;

@Data
public class ProductCreateDto {
    private String title;
    private String content;
    private Integer price;
    private String category;
    private Integer clickCount;

    public Product toEntity() {
        return Product.builder()
                .title(title)
                .content(content)
                .price(price)
                .category(category)
                .clickCount(clickCount)
                .build();
    }



}
