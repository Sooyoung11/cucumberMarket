package com.sohwakmo.cucumbermarket.controller;

import com.sohwakmo.cucumbermarket.domain.Product;
import com.sohwakmo.cucumbermarket.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage/count")
public class MypageRestCountController {

    private final ProductService productService;

    @GetMapping("/interested/{memberNo}")
    public ResponseEntity<Integer> interestedCount (@PathVariable Integer memberNo){
        Integer interestedCount = 0;

        List<Product> list = productService.interestedRead(memberNo);
        log.info("#of interested List Size", list.size());

        if(list.size() != 0)
        interestedCount = list.size();

        return ResponseEntity.ok(interestedCount);
    }
}
