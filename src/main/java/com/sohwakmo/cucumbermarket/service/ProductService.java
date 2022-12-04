package com.sohwakmo.cucumbermarket.service;

import com.sohwakmo.cucumbermarket.domain.Member;
import com.sohwakmo.cucumbermarket.domain.Product;
import com.sohwakmo.cucumbermarket.domain.ProductOfInterested;
import com.sohwakmo.cucumbermarket.dto.ProductOfInterestedRegisterOrDeleteOrCheckDto;
import com.sohwakmo.cucumbermarket.repository.MemberRepository;
import com.sohwakmo.cucumbermarket.repository.ProductOfInterestedRepository;
import com.sohwakmo.cucumbermarket.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final ProductOfInterestedRepository productOfInterestedRepository;

    public List<Product> read() { // 전체 상품 목록
        log.info("read()");

        return productRepository.findAll();
    }

    public Product read(Integer productNo) { // 상품 조회
        log.info("read(productNo = {})", productNo);

        return productRepository.findById(productNo).get();
    }


    @Transactional
    public Product update(Integer productNo) { // 상품 클릭 수 update
        log.info("update(productNo = {})", productNo);

        Product entity = productRepository.findById(productNo).get();
        log.info("entity = {}", entity);
        entity.updateClickCount(entity.getClickCount()+1);
        log.info("entity = {}", entity);

        Member member = memberRepository.findById(entity.getMember().getMemberNo()).get();
        log.info("member = {}", member);

        return entity;
    }

    public List<Product> search(String keyword) {
        log.info("search(keyword = {})", keyword);

        List<Product> list = productRepository.findByTitleIgnoreCaseContainingOrContentIgnoreCaseContainingOrderByProductNoDesc(keyword, keyword);
        log.info("list = {}", list);

        return list;
    }

    @Transactional
    public void addInterested(ProductOfInterestedRegisterOrDeleteOrCheckDto dto) {
        log.info("addInterested(dto = {}", dto);

        Member member = memberRepository.findById(dto.getMemberNo()).get();
        log.info("member = {}", member);

        Product product = productRepository.findById(dto.getProductNo()).get();
        log.info("product = {}", product);

        ProductOfInterested entity = ProductOfInterested.builder()
                        .member(member).product(product)
                        .build();
        log.info("entity = {}", entity);

        productOfInterestedRepository.save(entity);

        product.updateLikeCount(product.getLikeCount()+1);
        log.info("product = {}", product);

    }

    public void deleteInterested(ProductOfInterestedRegisterOrDeleteOrCheckDto dto) {
        log.info("deleteInterested(dto = {})", dto);

        Member member = memberRepository.findById(dto.getMemberNo()).get();
        log.info("member = {}", member);

        Product product = productRepository.findById(dto.getProductNo()).get();
        log.info("product = {}", product);

        ProductOfInterested entity = ProductOfInterested.builder()
                .member(member).product(product)
                .build();
        log.info("entity = {}", entity);

//        productOfInterestedRepository.delete(entity);
    }

    public String check(ProductOfInterestedRegisterOrDeleteOrCheckDto dto) {
        log.info("check(dto = {})", dto);

//        ProductOfInterested productOfInterested =productOfInterestedRepository.findByMemberNoAndProductNo(dto.getMemberNo(), dto.getProductNo());
//        log.info("productOfInterested = {}", productOfInterested);

        return null;
    }


}
