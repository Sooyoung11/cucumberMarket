package com.sohwakmo.cucumbermarket.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
    @Size(min = 5, max = 50)
    @Column(length = 50, nullable = false)
    private String title;

    @Size(min = 10)
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private String category;

    @Column(columnDefinition = "integer default 0") //조회수
    private Integer clickCount;


    private boolean status; // 거래상태


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

    public Product update(Integer clickCount) {
        this.clickCount = clickCount;

        return this;
    }

    public Product update(String title, String content, Integer price, String category) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.category = category;

        return this;
    }
}
