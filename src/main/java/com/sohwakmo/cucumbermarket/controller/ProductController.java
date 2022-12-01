package com.sohwakmo.cucumbermarket.controller;

import com.sohwakmo.cucumbermarket.domain.Product;
import com.sohwakmo.cucumbermarket.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
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
//        model.addAttribute("list", list);
            List<Product> products = new ArrayList<>();
            List<List<Product>> productsList = new ArrayList<>();

            for (int i = 0; i < list.size(); i++) {
                products.add(list.get(i));
                if ((i + 1) % 3 == 0) {
                    productsList.add(products);
                    products = new ArrayList<>();
                }
            }
            if (products.size() > 0) {
                productsList.add(products);
            }
            log.info(productsList.toString());
            log.info("list={}", list);

            model.addAttribute("list", productsList);


        return "/product/list";
    }

    @GetMapping("/detail")
    public String detail(Integer productNo, Model model) {
        log.info("datail(productNo = {})", productNo);

        Product product = productService.read(productNo);
        log.info("product = {}", product);


        model.addAttribute("product", product);
        return "/product/detail";
    }

}
