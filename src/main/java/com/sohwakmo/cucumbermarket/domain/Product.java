package com.sohwakmo.cucumbermarket.domain;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@Entity(name = "PRODUCTS")
@SequenceGenerator(name = "PRODUCTS_SEQ_GEN", sequenceName = "PRODUCTS_SEQ", allocationSize = 1)
public class Product extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBERS_SEQ_GEN")
    private Integer productNo;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private String category;

    @Column //조회수
    private Integer clickCount;


    private boolean status; // 거래상태


    private String photoUrl1;


    private String photoUrl2;


    private String photoUrl3;


    private String photoUrl4;


    private String photoUrl5;

    private String imgName;

    public Product update(Integer clickCount) {
        this.clickCount = clickCount;

        return this;
    }

    public Product update(String title, String content, Integer price, String category, String photoUrl1, String photoUrl2, String photoUrl3, String photoUrl4, String photoUrl5, String imgName) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.category = category;
        this.photoUrl1 = photoUrl1;
        this.photoUrl2 = photoUrl2;
        this.photoUrl3 = photoUrl3;
        this.photoUrl4 = photoUrl4;
        this.photoUrl5 = photoUrl5;

    return this;
    }
}
