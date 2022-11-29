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

    @Column
    private Integer memberNo;

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

    @Column
    private String photoUrl1;

    @Column
    private String photoUrl2;

    @Column
    private String photoUrl3;

    @Column
    private String photoUrl4;

    @Column
    private String photoUrl5;

    public Product update(String title, String content, Integer price, String category, boolean status, String photoUrl1, String photoUrl2, String photoUrl3, String photoUrl4, String photoUrl5) {
        this.title=title;
        this.content=content;
        this.price=price;
        this.category=category;
        this.status=status;
        this.photoUrl1=photoUrl1;
        this.photoUrl2=photoUrl2;
        this.photoUrl3=photoUrl3;
        this.photoUrl4=photoUrl4;
        this.photoUrl5=photoUrl5;

        return this;
    }

}
