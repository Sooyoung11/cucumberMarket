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
import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/profileImage")
public class ProfileImageController {

    private final MypageService mypageService;

    //마이페이지 모달창에서 사용자 정보 불러오기
    @GetMapping("/{memberNo}")
    @ResponseBody
    public ResponseEntity<ProfileImageReadDto> changeImage(@PathVariable Integer memberNo){
        log.info("changeImage(memberNo={})", memberNo);

        ProfileImageReadDto dto = mypageService.readProfileImage(memberNo);
        return  ResponseEntity.ok(dto);
    }

    //마이페이지 이미지 수정
    @PostMapping("/{modalMemberNo}")
    public ResponseEntity<Integer> updateImage(@PathVariable Integer modalMemberNo, @RequestBody ProfileImageReadDto dto) {
        log.info("updateImage(memberNo={}, dto={})", modalMemberNo, dto);

        dto.setMemberNo(modalMemberNo);

        Integer result = mypageService.updateImage(dto);
        return ResponseEntity.ok(result);
    }

    //마이페이지 이미지 업로드
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestBody MultipartFile userProfileImage) throws IOException {
        log.info("uploadImage(file={})", userProfileImage);

        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/images/mypage";
        File saveFile = new File(projectPath, userProfileImage.getOriginalFilename());
        userProfileImage.transferTo(saveFile);

        return ResponseEntity.ok("Image Upload Success");
    }


    //마이페이지 이미지 load
    @GetMapping("/userImage/{memberNo}")
    public ResponseEntity<ProfileImageReadDto> readUserImage(@PathVariable Integer memberNo){
        log.info("readUserImage(memberNo={})", memberNo);

        ProfileImageReadDto dto = mypageService.readProfileImage(memberNo);

        return ResponseEntity.ok(dto);
    }

//    @DeleteMapping("/delete/{memberNo}")
//    public ResponseEntity<String> deleteImage(@PathVariable Integer memberNo, @RequestBody ProfileImageReadDto dto){
//        log.info("deleteImage(memberNo={}, dto={})", memberNo, dto);
//
//        mypageService.deleteImage(dto);
//
//        return ResponseEntity.ok("delete image success");
//    }
}
