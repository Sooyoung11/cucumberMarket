package com.sohwakmo.cucumbermarket;

import com.sohwakmo.cucumbermarket.repository.ProductRepository;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class ProductSwimTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void test1() {
        System.out.println("test1()");


    }

}
