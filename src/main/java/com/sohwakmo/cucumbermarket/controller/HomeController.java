package com.sohwakmo.cucumbermarket.controller;

import com.sohwakmo.cucumbermarket.domain.Product;
import com.sohwakmo.cucumbermarket.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class HomeController {

    private final ProductService productService;
    @GetMapping("/")
    public String index(Model model) {
        log.info("index()");
        List<Product> productList= productService.readByLikeCountDesc();
        model.addAttribute("productList", productList);

        return "index";
    }

}
