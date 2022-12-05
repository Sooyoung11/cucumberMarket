package com.sohwakmo.cucumbermarket.controller;

import com.sohwakmo.cucumbermarket.domain.Product;
import com.sohwakmo.cucumbermarket.dto.ProductCreateDto;
import com.sohwakmo.cucumbermarket.dto.ProductFileDto;
import com.sohwakmo.cucumbermarket.dto.ProductUpdateDto;
import com.sohwakmo.cucumbermarket.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @GetMapping({"/detail", "/modify"})
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

        log.info(productsList.toString());
        log.info("list={}", list);

        model.addAttribute("list", productsList);

        return "/product/list";
    }

    @GetMapping("/addInterested")
    @ResponseBody
    public void addInterested(ProductOfInterestedRegisterOrDeleteOrCheckDto dto) {
        log.info("addInterested(dto = {})", dto);

        productService.addInterested(dto);
    @GetMapping("/create")
    public void create() {
        log.info("create()");

    }

    @DeleteMapping("/deleteInterested")
    @ResponseBody
    public void deleteInterested(ProductOfInterestedRegisterOrDeleteOrCheckDto dto) {
        log.info("deleteInterested(dto = {})", dto);

        productService.deleteInterested(dto);
    }
    @PostMapping("/create")
    public String create(ProductCreateDto dto, RedirectAttributes attrs) throws Exception {
        log.info("create(dto={})", dto);
        Product entity = productService.create(dto);

        attrs.addFlashAttribute("createProduct", entity.getProductNo());
         return "redirect:/product/list";
   }
    @PostMapping("/update")
    public String update(ProductUpdateDto dto, Model model) {
        log.info("update(dto={})", dto);

        Integer product = productService.update(dto);
        model.addAttribute("product", product);

        return "redirect:/product/detail?productNo=" + dto.getProductNo();
    }

    @GetMapping("/checkInterestedProduct")
    @ResponseBody
    public ResponseEntity<String> checkInterestedProduct(ProductOfInterestedRegisterOrDeleteOrCheckDto dto) {
        log.info("checkInterestedProduct(dto = {})", dto);

        String result = productService.checkInterestedProduct(dto);
        log.info("result = {}", result);

        return ResponseEntity.ok(result);
    }

    @GetMapping("interested")
    public String interestedPage(Integer memberNo, Model model) {
        log.info("interestedPage(memberNo = {})", memberNo);

        List<Product> list = productService.interestedRead(memberNo);
        log.info("list = {}", list);

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

        model.addAttribute("memberNo", memberNo);
        model.addAttribute("list", productsList);

        return "/product/interested";
    }


   @PostMapping("/delete")
   public String delete(Integer productNo){
       log.info("delete(productNo={})", productNo);
       productService.delete(productNo);
       return "redirect:/product/list";
   }


       // Integer productNoId = productService.delete(productNo);
       // attrs.addFlashAttribute("deleteProductNoId", productNoId);

       // return "redirect:/product/list";
  // }


}