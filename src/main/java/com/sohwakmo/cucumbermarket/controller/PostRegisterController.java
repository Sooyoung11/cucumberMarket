package com.sohwakmo.cucumbermarket.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/post/reply")
public class PostRegisterController {

    @DeleteMapping("/{image1Src}")
    public ResponseEntity<String> deleteImage(@PathVariable String image1Src){
        log.info("src={}", image1Src);
        return null;
    }
}
