package com.sohwakmo.cucumbermarket.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
public class TestController {

    @GetMapping("/test")
    public void test() {
        log.info("test()");
    }


    @PostMapping("/test2")
    public void test2(MultipartFile file) {
        log.info("test2(file ={})", file);
        log.info("file name : {}", file.getOriginalFilename());
        log.info("file size : {}", file.getSize());
    }


}
