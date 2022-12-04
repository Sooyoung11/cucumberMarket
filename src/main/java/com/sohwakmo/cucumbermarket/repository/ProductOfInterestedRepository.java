package com.sohwakmo.cucumbermarket.repository;

import com.sohwakmo.cucumbermarket.domain.ProductOfInterested;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductOfInterestedRepository  extends JpaRepository<ProductOfInterested, Integer> {
    ProductOfInterested findByMemberNoAndProductNo(Integer memberNo, Integer productNo);
}
