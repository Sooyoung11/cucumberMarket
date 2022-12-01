package com.sohwakmo.cucumbermarket.service;

import com.sohwakmo.cucumbermarket.domain.Product;
import com.sohwakmo.cucumbermarket.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> read() { // 전체 상품 조회
        log.info("read()");

        return productRepository.findAll();
    }

    public Product read(Integer productNo) { // 특정 상품 조회
        log.info("read(Integer = {})", productNo);

        return productRepository.findById(productNo).get();
    }
    
    public void insert() { // 상품 클릭 수 insert
        log.info("insert()");

    }
    

}
