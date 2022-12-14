package com.sohwakmo.cucumbermarket.controller;

import com.sohwakmo.cucumbermarket.domain.Post;
import com.sohwakmo.cucumbermarket.domain.Product;
import com.sohwakmo.cucumbermarket.service.PostService;
import com.sohwakmo.cucumbermarket.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class HomeController {

    private final ProductService productService;
    private final PostService postService;
    @GetMapping("/")
    public String index(Model model) {
        log.info("index()");
        List<Product> list= productService.readByLikeCountDesc();
        List<Product> productList= new ArrayList<>();
        for(Product p: list){
            if(productList.size()<8){
                productList.add(p);
            }
        }
        model.addAttribute("productList", productList);

        List<Post> list2= postService.readByClickCountDesc();
        List<Post> postList= new ArrayList<>();
        for(Post p: list2){
            if(postList.size()<10){
                postList.add(p);
            }
        }
        model.addAttribute("postList", postList);

        return "index";
    }

}
