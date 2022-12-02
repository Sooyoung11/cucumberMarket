package com.sohwakmo.cucumbermarket.controller;

import com.sohwakmo.cucumbermarket.dto.ProfileImageReadDto;
import com.sohwakmo.cucumbermarket.service.MypageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/profileImage")
public class ProfileImageController {

    private final MypageService mypageService;

    @GetMapping("/{memberNo}")
    @ResponseBody
    public ResponseEntity<ProfileImageReadDto> changeImage(@PathVariable Integer memberNo){
        log.info("changeImage(memberNo={})", memberNo);

        ProfileImageReadDto dto = mypageService.readProfileImage(memberNo);
        return  ResponseEntity.ok(dto);
    }

    @PutMapping("/{modalMemberNo}")
    public ResponseEntity<Integer> updateImage(@PathVariable Integer modalMemberNo, @RequestBody ProfileImageReadDto dto){
        log.info("updateImage(memberNo={}, dto={}, userImageFile={})", modalMemberNo, dto);

        dto.setMemberNo(modalMemberNo);

        Integer result = mypageService.updateImage(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/userImage/{memberNo}")
    public ResponseEntity<ProfileImageReadDto> readUserImage(@PathVariable Integer memberNo){
        log.info("readUserImage(memberNo={})", memberNo);

        ProfileImageReadDto dto = mypageService.readProfileImage(memberNo);

        return ResponseEntity.ok(dto);
    }
}
