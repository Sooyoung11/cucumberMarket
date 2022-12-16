package com.sohwakmo.cucumbermarket.controller;

import com.sohwakmo.cucumbermarket.dto.MypageReadDto;
import com.sohwakmo.cucumbermarket.dto.MypageUpdateDto;
import com.sohwakmo.cucumbermarket.service.MypageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/mypage")
@Controller
public class MypageController {

    private final MypageService mypageService;
    private final PasswordEncoder passwordEncoder;
    
    //마이페이지 호출
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/mymain")
    public void mypage(Integer memberNo, Model model){

        log.info("mypage(memberNo={})", memberNo);

        MypageReadDto loginUser = mypageService.loadProfile(memberNo);
        log.info(loginUser.toString());
        model.addAttribute("userProfile", loginUser);

    }

    //마이페이지 수정page 호출
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/modify")
    public void moodify(Integer memberNo, Model model){
        log.info("modify(memberId={})", memberNo);
        MypageReadDto userInfo = mypageService.loadProfile(memberNo);
        log.info("modifyReadDto={}", userInfo);

        model.addAttribute("userProfile", userInfo);
    }

    //마이페이지 회원정보 수정
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/update")
    public String update(MypageUpdateDto dto, String oauth){
        log.info("update(dto={}, oauth={})", dto, oauth);

        if(oauth ==null)
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));

        Integer memberNo = mypageService.update(dto);

        return "redirect:/mypage/mymain?memberNo="+ memberNo;
    }
    


}
