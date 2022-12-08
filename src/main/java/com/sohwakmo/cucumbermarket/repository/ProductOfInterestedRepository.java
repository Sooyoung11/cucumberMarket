package com.sohwakmo.cucumbermarket.repository;

import com.sohwakmo.cucumbermarket.domain.Member;
import com.sohwakmo.cucumbermarket.domain.Product;
import com.sohwakmo.cucumbermarket.domain.ProductOfInterested;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductOfInterestedRepository  extends JpaRepository<ProductOfInterested, Integer> {
    Optional<ProductOfInterested> findByMemberAndProduct(Member member, Product product);

    // delete table products_of_interested where member_no = ? and product_no = ?
    void deleteByMemberAndProduct(Member member, Product product);

    // select * from products_of_interested where member_no = ?
    List<ProductOfInterested> findByMember(Member Member);

    // select * from products_of_interested where product_no = ?
    ProductOfInterested findByProduct(Product product);

    // delete table products_of_interested where product_no = ?
    void deleteByProduct(Product product);

}
