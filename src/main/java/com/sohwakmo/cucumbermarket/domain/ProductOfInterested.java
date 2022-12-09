package com.sohwakmo.cucumbermarket.domain;


import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@Entity(name = "PRODUCTS_OF_INTERESTED")
@SequenceGenerator(name = "PRODUCTS_OF_INTERESTED_SEQ_GEN", sequenceName = "PRODUCTS_OF_INTERESTED_SEQ", allocationSize = 1)
public class ProductOfInterested {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCTS_OF_INTERESTED_SEQ_GEN")
    private Integer no;

    private Integer member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

}
