package com.sohwakmo.cucumbermarket.controller;

import com.sohwakmo.cucumbermarket.domain.Member;
import com.sohwakmo.cucumbermarket.domain.Product;
import com.sohwakmo.cucumbermarket.domain.ProductOfInterested;
import com.sohwakmo.cucumbermarket.dto.ProductCreateDto;
import com.sohwakmo.cucumbermarket.dto.ProductOfInterestedRegisterOrDeleteOrCheckDto;
import com.sohwakmo.cucumbermarket.dto.ProductUpdateDto;
import com.sohwakmo.cucumbermarket.service.MemberService;
import com.sohwakmo.cucumbermarket.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.*;


@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final MemberService memberService;

    @GetMapping("/list")
    public String list(Model model, Integer memberNo, String type, String keyword,
                       @PageableDefault(page = 0, size = 2, sort = "productNo", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("list()");

        Page<Product> list;

        if (keyword == null) {
            list = productService.read(pageable);
        } else {
            list = productService.search(type, keyword, pageable);
        }
        log.info("list = {}", list);

        String result;
        if(list.isEmpty() == true) { // 리스트가 비어있으면
            log.info("nok");
            result = "nok";
        } else {
            log.info("ok");
            result = "ok";
        }

        int nowPage = list.getPageable().getPageNumber(); // 페이지 0부터 시작해서 +1
//        int startPage = Math.max(1, nowPage-2);
        int startPage = (list.getPageable().getPageNumber()/5)*5 +1;
        int endPage = Math.min(nowPage+2, list.getTotalPages());
        if(endPage  <=0){
            startPage =1;
            endPage =1;
        } else  {
            if(list.getTotalPages() < 6) {
                startPage = 1;
                endPage = list.getTotalPages();
            } else {
                if (list.getPageable().getPageNumber() < 6) {
                    startPage = 1;
                    endPage = 5;
                } else {
                    startPage = (list.getPageable().getPageNumber()/ 5)*5 +1;
                    endPage= Math.min(startPage, list.getTotalPages());
                    }
//                    startPage = (list.getPageable().getPageNumber()/5)*5 +1;
//                    endPage = Math.min(nowPage, (nowPage / 5)*5 +5);
                }
            }

        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        model.addAttribute("result", result);
        model.addAttribute("type", type);
        model.addAttribute("list", list);


        // 찜 개수
        Integer interestedCount = 0;
        List<Product> likeList = productService.interestedRead(memberNo);

        if(likeList.size() != 0)
            interestedCount = likeList.size();

        model.addAttribute("interestedList",interestedCount);

        return "/product/list";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/detail")
    public String detail(Integer productNo, Model model,Integer memberNo, HttpSession session) {
        log.info("datail(productNo = {})", productNo);

        Product product = productService.detail(productNo);
        product.setContent(product.getContent().replace("\n", "<br/>")); // 컨텐트 내용 줄바꿈 처리
        log.info("product = {}", product);

        model.addAttribute("product", product); // 상품 정보
        model.addAttribute("member", product.getMember()); // 상품 올린 사람의 정보

        // 최근 본 목록
        String productNo1 = product.getProductNo().toString(); // 상품 번호
        String photo = product.getPhotoUrl1(); // 상품 사진

        ArrayList<String> productlist = (ArrayList) session.getAttribute("productlist");

        // 최근 본 상품 생성
        if(productlist==null ) {
            productlist = new ArrayList<>();
            session.setAttribute("productlist",productlist);
        }

        if(productlist.size() > 5){ // 최근 본 상품 3개로 제한두기
            if(!productlist.contains(productNo1)) { // 중복 확인
                productlist.remove(0);
                productlist.remove(0);
            }

            if(photo == null){ // 사진이 default 값이면
                if(!productlist.contains(productNo1)){ // 중복 확인
                    productlist.add(4, "/images/product/noimg.png");
                    productlist.add(5, productNo1);
                }

            }else{
                if(!productlist.contains(productNo1)) { // 중복 확인
                    productlist.add(4, photo);
                    productlist.add(5, productNo1);
                }
            }

        } else { // 사진이 default 값이면
            if(photo == null){
                if(!productlist.contains(productNo1)) { // 중복 확인
                    productlist.add("/images/product/noimg.png");
                    productlist.add(productNo1);
                }
            }else{
                if(!productlist.contains(productNo1)) { // 중복 확인
                    productlist.add(photo);
                    productlist.add(productNo1);
                }
            }
        }


        return "/product/detail";
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

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/interested")
    public String interestedPage(Integer memberNo, Model model, @PageableDefault(page = 0, size = 8, sort = "productProductNo", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("interestedPage(memberNo = {})", memberNo);

        Page<ProductOfInterested> list = productService.interestedRead(memberNo, pageable);
        log.info("list = {}", list);

        int nowPage = list.getPageable().getPageNumber() +1; // 페이지 0부터 시작해서 +1
        int startPage = Math.max(1, nowPage - 2);
        int endPage =  Math.min(nowPage + 2, list.getTotalPages());
        if(startPage <= 0 || endPage <=0){
            startPage =1;
            endPage =1;
        }
        if(nowPage == 1 || nowPage ==2 || nowPage ==3){
            endPage =5;
        }

        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);


        model.addAttribute("memberNo", memberNo);
        model.addAttribute("list", list);

        return "/product/interested";
    }

    //마이페이지 판매목록(진행중, 거래완료) 호출
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/myList/searchStatus")
    public String searchStatus(Integer myProductListSelect, Integer memberNo, Model model, @PageableDefault(page = 0, size = 8, sort = "productNo", direction = Sort.Direction.DESC) Pageable pageable){
        log.info("searchStatus(myProductListSelect={}, memberNo={})", myProductListSelect, memberNo);


        Page<Product> list = null;
        switch (myProductListSelect){
            case 1:
                list = productService.myProductListRead(memberNo, pageable);
                log.info("myProductListRead list = {}", list);
                break;
            case 2:
                list = productService.proceedListRead(memberNo, pageable);
                log.info("proceedListRead list = {}", list);
                break;
            case 3:
                list = productService.completedListRead(memberNo, pageable);
                log.info("completedListRead list = {}", list);
                break;
            case 4:
                list = productService.buyMyListRead(memberNo, pageable);
                log.info("list = {}", list);
                break;

        }

        int nowPage = list.getPageable().getPageNumber() +1; // 페이지 0부터 시작해서 +1
        int startPage = Math.max(1, nowPage - 2);
        int endPage =  Math.min(nowPage + 2, list.getTotalPages());
        if(startPage <= 0 || endPage <=0){
            startPage =1;
            endPage =1;
        }

        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        model.addAttribute("list", list);
        model.addAttribute("selectedValue", myProductListSelect);

        return "/product/myList";
    }


    @GetMapping("/ing")
    @ResponseBody
    public ResponseEntity<String> dealStatusIng(Integer productNo) {
        log.info("dealStatusIng(productNo = {})", productNo);

        productService.dealStatusIng(productNo);

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

        //매너온도 +1.5
        member.gradeUpdate(member.getGrade()+2.5);

        Product product = ProductCreateDto.builder()
                .title(dto.getTitle()).content(dto.getContent()).price(dto.getPrice()).category(dto.getCategory()).clickCount(dto.getClickCount()).likeCount(dto.getLikeCount()).dealAddress(dto.getDealAddress()).member(member).build().toEntity();

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