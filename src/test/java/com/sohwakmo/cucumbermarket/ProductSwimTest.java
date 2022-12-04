package com.sohwakmo.cucumbermarket;

import com.sohwakmo.cucumbermarket.domain.Product;
import com.sohwakmo.cucumbermarket.repository.ProductOfInterestedRepository;
import com.sohwakmo.cucumbermarket.repository.ProductRepository;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
@Slf4j
public class ProductSwimTest {

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

    @Test
    public void test2() {
        log.info("test2()");

        Assertions.assertNotNull(productOfInterestedRepository);

//        productOfInterestedRepository.deleteById(1);

    }

}
