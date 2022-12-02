package com.sohwakmo.cucumbermarket.controller;

import com.sohwakmo.cucumbermarket.domain.Post;
import com.sohwakmo.cucumbermarket.service.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/post/reply")
public class PostRegisterController {

    private final PostService postService;

    @DeleteMapping("/{image1Src}")
    public ResponseEntity<String> deleteImage(@PathVariable String image1Src){
        log.info("src={}", image1Src);
        String result = postService.chekImageNumandDeleteImage(image1Src);
        log.info(result);
        return ResponseEntity.ok("성공");
    }
}
