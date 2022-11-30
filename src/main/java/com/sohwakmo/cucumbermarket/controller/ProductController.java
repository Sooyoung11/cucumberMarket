package com.sohwakmo.cucumbermarket.controller;

import com.sohwakmo.cucumbermarket.domain.Product;
import com.sohwakmo.cucumbermarket.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/list")
    public String list(Model model) {
        log.info("list()");

        List<Product> list = productService.read();
        model.addAttribute("list", list);

        log.info("list={}", list);

        return "/product/list";
    }

    @GetMapping("/detail")
    public String detail() {
        log.info("datail()");

        return "/product/detail";
    }

}
