package com.sohwakmo.cucumbermarket.controller;

import com.sohwakmo.cucumbermarket.domain.Product;
import com.sohwakmo.cucumbermarket.dto.ProductCreateDto;
import com.sohwakmo.cucumbermarket.dto.ProductUpdateDto;
import com.sohwakmo.cucumbermarket.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

        List<List<Product>> productsList = new ArrayList<>();
        List<Product> products = new ArrayList<>();

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

        Product entity = productService.update(productNo); // 조회수 값 증가
        log.info("entity = {}", entity);

        model.addAttribute("product", entity);
        model.addAttribute("member", entity.getMember());

        return "/product/detail";
    }

    @GetMapping("/search")
    public String search(String keyword, Model model) {
        log.info("search(keyword = {})", keyword);

        List<Product> list = productService.search(keyword);


        List<List<Product>> productsList = new ArrayList<>();
        List<Product> products = new ArrayList<>();

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


        model.addAttribute("list", productsList);

        return "/product/list";
    }


    @GetMapping("/create")
    public void create() {
        log.info("create()");

    }

    @PostMapping("/create")
    public String create(ProductCreateDto dto, RedirectAttributes attrs) {
        log.info("create(dto={})", dto);
        Product entity = productService.create(dto);
        attrs.addFlashAttribute("createdId", entity.getProductNo());

        return "redirect:/product/list";
   }

   @GetMapping("/modify")
    public void modify(Integer productNo, Model model) {
        log.info("modify(productNo={})", productNo);

        Product product = productService.read(productNo);

        model.addAttribute("product", product);
   }

   @PostMapping("/update")
    public String update(ProductUpdateDto dto) {
        log.info("update(dto={})", dto);

        Integer productNo = productService.update(dto);

        return "redirect:/product/detail?productNo=" + dto.getProductNo();
   }

}