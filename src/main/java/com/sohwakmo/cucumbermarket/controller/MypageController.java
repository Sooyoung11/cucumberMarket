package com.sohwakmo.cucumbermarket.controller;

import com.sohwakmo.cucumbermarket.dto.MypageReadDto;
import com.sohwakmo.cucumbermarket.dto.MypageUpdateDto;
import com.sohwakmo.cucumbermarket.service.MypageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    @GetMapping("/mymain")
    public void mypage(Integer memberNo, Model model){

        log.info("mypage(memberNo={})", memberNo);

        MypageReadDto loginUser = mypageService.loadProfile(memberNo);
        log.info(loginUser.toString());
        model.addAttribute("userProfile", loginUser);

    }

    @GetMapping("/modify")
    public void moodify(Integer memberNo, Model model){
        log.info("modify(memberId={})", memberNo);
        MypageReadDto userInfo = mypageService.loadProfile(memberNo);
        log.info("modifyReadDto={}", userInfo);

        model.addAttribute("userProfile", userInfo);
    }

    @PostMapping("/update")
    public String update(MypageUpdateDto dto){
        log.info("update(dto={})", dto);

        Integer memberNo = mypageService.update(dto);

        return "redirect:/mypage/mymain?memberNo="+ memberNo;
    }



    @PostMapping("/mymain/upload")
    public void uploadFile(@RequestParam("uploadfile") File uploadfile, Model model){
        log.info("uploadFile(uploadfile={})", uploadfile);
    }


}
