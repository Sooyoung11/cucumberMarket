package com.sohwakmo.cucumbermarket.controller;

import com.sohwakmo.cucumbermarket.domain.Member;
import com.sohwakmo.cucumbermarket.domain.Product;
import com.sohwakmo.cucumbermarket.service.MypageService;
import com.sohwakmo.cucumbermarket.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage/count")
public class MypageRestCountController {

    private final ProductService productService;
    private final MypageService mypageService;

    //마이페이지 찜목록 count
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/interested/{memberNo}")
    public ResponseEntity<Integer> interestedCount (@PathVariable Integer memberNo){
        log.info("interestedCount(memberNo={})", memberNo);
        Integer interestedCount = 0;

        List<Product> list = productService.interestedRead(memberNo);
        log.info("#of interested List Size={}", list.size());

        if(list.size() != 0)
        interestedCount = list.size();

        return ResponseEntity.ok(interestedCount);
    }

    //마이페이지 판매목록-진행중 count
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/proceeding/{memberNo}")
    public ResponseEntity<Integer> proceedingCount(@PathVariable Integer memberNo){
        log.info("proceedingCount(memberNo={})",memberNo);
        Integer proceedingCount = 0;

        List<Product> list = productService.proceedListRead(memberNo);
        log.info("#of proceeding list size={}", list.size());

        if(list.size() != 0)
        proceedingCount = list.size();

        return ResponseEntity.ok(proceedingCount);
    }

    //마이페이지 판매목록-거래완료 count
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/completed/{memberNo}")
    public ResponseEntity<Integer> completedList(@PathVariable Integer memberNo){
        log.info("completedCount(memberNo={})", memberNo);
        Integer completedCount = 0;

        List<Product> list = productService.completedListRead(memberNo);
        log.info("#of completed list size={} ", list.size() );

        if(list.size() != 0)
            completedCount = list.size();

        return ResponseEntity.ok(completedCount);
    }


    //마이페이지 구매목록 count
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/buyMyList/{memberNo}")
    public ResponseEntity<Integer> buyMyListCount(@PathVariable Integer memberNo){
        log.info("buyMyListCount(memberNo={})", memberNo);
        Integer buyMyListCount = 0;

        List<Product> list = productService.buyMyListRead(memberNo);
        log.info("#of buyMyList size={}", list.size());

        if(list.size() != 0){
            buyMyListCount = list.size();
        }

        return ResponseEntity.ok(buyMyListCount);
    }

    //매너온도
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/checkTemp/{memberNo}")
    public ResponseEntity<Double> checkMyTemp(@PathVariable Integer memberNo){
        log.info("checkMyTemp(memberNo={})", memberNo);

        Double myTemp = mypageService.readUserTemp(memberNo);
        log.info("myTemp={}", myTemp);

        return ResponseEntity.ok(myTemp);
    }


}
