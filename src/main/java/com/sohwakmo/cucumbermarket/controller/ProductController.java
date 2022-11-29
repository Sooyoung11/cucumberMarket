package com.sohwakmo.cucumbermarket.controller;

import com.sohwakmo.cucumbermarket.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/list")
    public void list() {
        log.info("list()");


    }

    @GetMapping("/detail")
    public String detail() {
        log.info("datail()");

        return "/product/detail";
    }

}
