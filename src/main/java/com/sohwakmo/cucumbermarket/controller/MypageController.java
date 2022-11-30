package com.sohwakmo.cucumbermarket.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Slf4j
@RequestMapping("/mypage")
@Controller
public class MypageController {
    @GetMapping("/mymain")
    public void mypage(){
        log.info("mypage()");
    }

    @PostMapping("/mymain")
    public void uploadFile(@RequestParam("uploadfile") File uploadfile, Model model){
        log.info("uploadFile(uploadfile={})", uploadfile);



    }
}
