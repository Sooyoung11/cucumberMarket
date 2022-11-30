package com.sohwakmo.cucumbermarket.controller;

import com.sohwakmo.cucumbermarket.dto.MemberRegisterDto;
import com.sohwakmo.cucumbermarket.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public void join(){
        log.info("join() GET");
    }

    @GetMapping("/check_id")
    @ResponseBody
    public ResponseEntity<String> checkMemberId(String memberId){
        log.info("checkMemberId(memberId= {})", memberId);

        String result= memberService.checkMemberId(memberId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/join")
    public String join(MemberRegisterDto dto){
        log.info("join(dto= {}) POST", dto);

        memberService.registerMember(dto);
        return "redirect:/";
    }
}
