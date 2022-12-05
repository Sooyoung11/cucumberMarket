package com.sohwakmo.cucumbermarket;

import com.sohwakmo.cucumbermarket.domain.Member;
import com.sohwakmo.cucumbermarket.domain.Product;
import com.sohwakmo.cucumbermarket.domain.ProductOfInterested;
import com.sohwakmo.cucumbermarket.repository.MemberRepository;
import com.sohwakmo.cucumbermarket.repository.ProductOfInterestedRepository;
import com.sohwakmo.cucumbermarket.repository.ProductRepository;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@SpringBootTest
@Slf4j
public class ProductSwimTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductOfInterestedRepository productOfInterestedRepository;

//    @Test
    public void test1() {
        log.info("test1()");

        Assertions.assertNotNull(productRepository);

        List<Product> list = productRepository.findAll();
        for (Product p : list) {
            log.info(p.toString());
        }
    }

    @Transactional
    @Test
    public void test2() {
        log.info("test2()");

        Assertions.assertNotNull(productOfInterestedRepository);
        Assertions.assertNotNull(memberRepository);
        Assertions.assertNotNull(productRepository);

        Member member = memberRepository.findById(1).get();
        log.info("member = {}", member);
//        Product product = productRepository.findById(8).get();
//        log.info("product = {}", product);


        List<ProductOfInterested> list = productOfInterestedRepository.findByMember(member);
        log.info("list = {}", list);
    }

}
