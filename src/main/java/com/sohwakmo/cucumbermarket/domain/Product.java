package com.sohwakmo.cucumbermarket.domain;


import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity(name = "PRODUCTS")
@SequenceGenerator(name = "PRODUCTS_SEQ_GEN", sequenceName = "PRODUCTS_SEQ", allocationSize = 1)
public class Product extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCTS_SEQ_GEN")
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

    @Column(columnDefinition = "integer default 0") //조회수.
    private Integer clickCount;

    @Column(columnDefinition = "integer default 0")
    private Integer likeCount;
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

    private Integer boughtMemberNo;

    private String dealAddress;

    public Product updateClickCount(Integer clickCount) {
        this.clickCount = clickCount;

        return this;
    }

    public Product updateLikeCount(Integer likeCount) {
        this.likeCount = likeCount;

        return this;
    }

    public Product updateStatusAndBoughtMemberNo(boolean status, Integer boughtMemberNo) {
        this.status = status;
        this.boughtMemberNo = boughtMemberNo;

        return this;
    }


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
