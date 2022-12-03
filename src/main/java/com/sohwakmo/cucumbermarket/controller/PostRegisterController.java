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

    @DeleteMapping("/{imageSrc}")
    public ResponseEntity<String> delete01Image(@PathVariable String imageSrc) throws Exception{
        log.info("src={}", imageSrc);
        String result = postService.chekImageNumandDeleteImage(imageSrc);
        log.info(result);
        return ResponseEntity.ok("성공");
    }

}
