package com.sohwakmo.cucumbermarket.controller;

import com.sohwakmo.cucumbermarket.domain.Member;
import com.sohwakmo.cucumbermarket.domain.Product;
import com.sohwakmo.cucumbermarket.dto.ProductCreateDto;
import com.sohwakmo.cucumbermarket.dto.ProductOfInterestedRegisterOrDeleteOrCheckDto;
import com.sohwakmo.cucumbermarket.dto.ProductUpdateDto;
import com.sohwakmo.cucumbermarket.service.MemberService;
import com.sohwakmo.cucumbermarket.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final MemberService memberService;

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

        Product product = productService.detail(productNo);
        log.info("product = {}", product);

        model.addAttribute("product", product); // 상품 정보
        model.addAttribute("member", product.getMember()); // 상품 올린 사람의 정보

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

        String result;
        if( list.size() == 0) { // 검색 결과가 없으면
            result = "nok";
        } else {
            result = "ok";
        }
        model.addAttribute("result", result);

        model.addAttribute("list", productsList);

        return "/product/list";
    }

    @GetMapping("/addInterested")
    @ResponseBody
    public void addInterested(ProductOfInterestedRegisterOrDeleteOrCheckDto dto) {
        log.info("addInterested(dto = {})", dto);

        productService.addInterested(dto);
    }

    @DeleteMapping("/deleteInterested")
    @ResponseBody
    public void deleteInterested(ProductOfInterestedRegisterOrDeleteOrCheckDto dto) {
        log.info("deleteInterested(dto = {})", dto);
        
        productService.deleteInterested(dto);
    }

    @GetMapping("/checkInterestedProduct")
    @ResponseBody
    public ResponseEntity<String> checkInterestedProduct(ProductOfInterestedRegisterOrDeleteOrCheckDto dto) {
        log.info("checkInterestedProduct(dto = {})", dto);

        String result = productService.checkInterestedProduct(dto);
        log.info("result = {}", result);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/interested")
    public String interestedPage(Integer memberNo, Model model) {
        log.info("interestedPage(memberNo = {})", memberNo);

        List<Product> list = productService.interestedRead(memberNo);
        log.info("list = {}", list);

        List<List<Product>> productsList = listRead(list);

        model.addAttribute("memberNo", memberNo);
        model.addAttribute("list", productsList);

        return "/product/interested";
    }

    @GetMapping("/myList")
    public String myList(Integer memberNo, Model model) {
        log.info("myList()");

        List<Product> list = productService.myProductListRead(memberNo);
        log.info("list = {}", list);

        List<List<Product>> productsList = listRead(list);

        model.addAttribute("list", productsList);

        return "/product/myList";
    }

    //마이페이지 판매목록(진행중, 거래완료) 호출
    @GetMapping("/myList/searchStatus")
    public String searchStatus(Integer myProductListSelect, Integer memberNo, Model model){
        log.info("searchStatus(myProductListSelect={}, memberNo={})", myProductListSelect, memberNo);

        List<Product> list = null;
        switch (myProductListSelect){
            case 1:
                list = productService.myProductListRead(memberNo);
                log.info("myProductListRead list = {}", list);
                break;
            case 2:
                list = productService.proceedListRead(memberNo);
                log.info("proceedListRead list = {}", list);
                break;
            case 3:
                list = productService.completedListRead(memberNo);
                log.info("completedListRead list = {}", list);
                break;
            case 4:
                list = productService.buyMyListRead(memberNo);
                log.info("list = {}", list);
                break;

        }

        List<List<Product>> productsList = listRead(list);

        model.addAttribute("list", productsList);

        return "/product/myList";
    }

    //ListRead() 함수
    public List<List<Product>> listRead(List<Product> list){

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

        return productsList;
    }


    @GetMapping("/ing")
    @ResponseBody
    public ResponseEntity<String> dealStatusIng(Integer productNo, Integer boughtMemberNo) {
        log.info("dealStatusIng(productNo = {}, boughtMemberNo = {})", productNo, boughtMemberNo);

        productService.dealStatusIng(productNo, boughtMemberNo);

        return ResponseEntity.ok("hello");
    }

    @GetMapping("/done")
    @ResponseBody
    public ResponseEntity<String> dealStatusDone(Integer productNo, Integer boughtMemberNo) {
        log.info("dealStatusDone(productNo = {}, boughtMemberNo = {})", productNo, boughtMemberNo);

        productService.dealStatusDone(productNo, boughtMemberNo);

        return ResponseEntity.ok("ok");
    }

    @GetMapping("/isDealStatus")
    @ResponseBody
    public ResponseEntity<String> isDealStatus(Integer productNo) {
        log.info("isDealStatus(productNo = {})", productNo);

        Product product = productService.isDealStatus(productNo);
        log.info("product(BoughtMemberNo = {})", product.getBoughtMemberNo());

        String result;
        if (product.getBoughtMemberNo() == null) {
            result = "nok";
        } else {
            result = "ok";
        }
        log.info("result = {}", result);

        return ResponseEntity.ok(result);
    }

    // 상품 등록 페이지 이동
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/create")
    public String create() {
        log.info("create()");

        return "product/create";
    }

    //상품 등록
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public String create(
            @RequestParam(value = "imgFile", required = false) List<MultipartFile> imgFile,
            ProductCreateDto dto, Integer memberNo) throws Exception {

        log.info("imgFile={}", imgFile);
        Member member = memberService.findMemberByMemberNo(memberNo);

        Product product = ProductCreateDto.builder()
                .title(dto.getTitle()).content(dto.getContent()).price(dto.getPrice()).category(dto.getCategory()).clickCount(dto.getClickCount()).likeCount(dto.getLikeCount()).member(member).build().toEntity();

        for (MultipartFile multipartFile : imgFile) {
            log.info("imgFile={}", imgFile);
            log.info("multipartFile={}", multipartFile);

            if (multipartFile.isEmpty()) {
                Product products = productService.create(product);
            } else {
                Product products = productService.create(product, multipartFile);
            }
        }
        return "redirect:/product/list";
    }

    // 상품 수정 페이지로 이동
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/modify")
    public String modify(Integer productNo, Model model) {
        log.info("modify(productNo={})", productNo);

        Product product = productService.read(productNo);
        model.addAttribute("product", product);

        return "/product/modify";
    }

    // 상품 수정
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/update")
    public String update(ProductUpdateDto dto) {
        log.info("update(dto={})", dto);

        Integer productNo = productService.update(dto);

        return "redirect:/product/detail?productNo=" + dto.getProductNo();
    }

    // 상품 삭제//
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/delete")
    public String delete(Integer productNo) {
        log.info("delete(productNo={})", productNo);

        productService.delete(productNo);

        return "redirect:/product/list";
    }

}

