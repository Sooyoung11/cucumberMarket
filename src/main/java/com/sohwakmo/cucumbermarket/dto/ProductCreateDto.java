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
    private Integer likeCount;

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

    private Integer memberNo;




}
