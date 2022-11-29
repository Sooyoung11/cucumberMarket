package com.sohwakmo.cucumbermarket.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("/mypage")
@Controller
public class MypageController {
    @GetMapping("/mymain")
    public void mypage(){
        log.info("mypage()");
    }
}
