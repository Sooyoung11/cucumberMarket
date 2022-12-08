package com.sohwakmo.cucumbermarket.dto;

import com.sohwakmo.cucumbermarket.domain.Product;
import lombok.Data;

@Data
public class ProductUpdateDto {
    private Integer productNo;
    private String title;
    private String content;
    private Integer price;
    private String category;

    public Product toEntity() {
        return Product.builder()
                .productNo(productNo)
                .title(title)
                .content(content)
                .price(price)
                .category(category)
                .build();
    }
}
