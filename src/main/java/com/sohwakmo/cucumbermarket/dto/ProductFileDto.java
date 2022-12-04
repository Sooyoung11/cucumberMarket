package com.sohwakmo.cucumbermarket.dto;

import com.sohwakmo.cucumbermarket.domain.Product;
import lombok.Data;

@Data
public class ProductFileDto {
    private Integer productNo;

    private String photoUrl1;
    private String photoUrl2;
    private String photoUrl3;
    private String photoUrl4;
    private String photoUrl5;

    private String photoName1;
    private String photoName2;
    private String photoName3;
    private String photoName4;
    private String photoName5;

    public Product toEntity() {
        return Product.builder()
                .productNo(productNo)
                .photoUrl1(photoUrl1)
                .photoUrl2(photoUrl2)
                .photoUrl3(photoUrl3)
                .photoUrl4(photoUrl4)
                .photoUrl5(photoUrl5)
                .photoName1(photoName1)
                .photoName2(photoName2)
                .photoName3(photoName3)
                .photoName4(photoName4)
                .photoName5(photoName5)
                .build();
    }

}
