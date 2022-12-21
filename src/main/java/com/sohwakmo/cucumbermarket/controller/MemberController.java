package com.sohwakmo.cucumbermarket.controller;

import com.sohwakmo.cucumbermarket.domain.Member;
import com.sohwakmo.cucumbermarket.dto.MemberRegisterDto;
import com.sohwakmo.cucumbermarket.dto.ResetPasswordDto;
import com.sohwakmo.cucumbermarket.service.EmailServiceImpl;
import com.sohwakmo.cucumbermarket.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;
    private final EmailServiceImpl emailService;

    private final AuthenticationManager authenticationManager;
    @Value("${cos.key}")
    private String cosKey;


    @GetMapping("/login")
    public void login(@RequestParam(value = "error", required = false)String error,
                      @RequestParam(value = "exception", required = false)String exception,
                      Model model){
        log.info("loginPage");
        log.info("error={}, exception={}", error, exception);
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
    }

    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(String code){

        MemberRegisterDto kakaoMember = memberService.socialLogin(code);

        //카카오 로그인 요청한 사용자가 가입자인지 확인
        Member originMember = memberService.findRegisterMember(kakaoMember.getMemberId());

        //카카오 로그인 요청한 사용자가 비가입자이면 회원가입
        if(originMember.getMemberId() == null) {
            memberService.registerMember(kakaoMember);
        }

        //카카오 로그인 요청한 사용자 자동 로그인
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoMember.getMemberId(), cosKey));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/";
    }

    @GetMapping("/member/join")
    public void join(){
        log.info("join() GET");
    }

    @GetMapping("/member/check_memberid")
    @ResponseBody
    public ResponseEntity<String> checkMemberId(String memberId){
        log.info("checkMemberId(memberId= {})", memberId);

        String result= memberService.checkMemberId(memberId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/member/check_password")
    @ResponseBody
    public ResponseEntity<String> checkPassword(@RequestParam String password){
        log.info("checkPassword(password= {})", password);

        String result= memberService.checkPassword(password);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/member/check_password2")
    @ResponseBody
    public ResponseEntity<String> checkPassword2(String password, String password2){
        log.info("checkPassword2(password= {}, password2= {})", password, password2);

        String result= memberService.checkPassword2(password, password2);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/member/check_nickname")
    @ResponseBody
    public ResponseEntity<String> checkNickname(String nickname){
        log.info("checkNickname(nickname= {})", nickname);

        String result= memberService.checkNickname(nickname);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/member/check_email")
    @ResponseBody
    public ResponseEntity<String> checkEmail(String email){
        log.info("checkEmail(email= {})", email);

        String result= memberService.checkEmail(email);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/member/join")
    public String join(MemberRegisterDto dto){
        log.info("join(dto= {}) POST", dto);

        memberService.registerMember(dto);
        return "redirect:/login";
    }

    @GetMapping("/member/find/id")
    public void findId(){
        log.info("findId() GET");
    }

    @PostMapping("/member/find/id")
    public String findId(String email, RedirectAttributes attrs){
        log.info("findId(email= {}) POST", email);
        Member member= memberService.findId(email);
        String memberId=  member.getMemberId();
        log.info("findId(memberId= {})", memberId);
        attrs.addFlashAttribute("memberId", memberId);
        return "redirect:/member/find/id";
    }

    @GetMapping("/member/find/pw")
    public void findPw(){
        log.info("findPw() GET");
    }

    @PostMapping("/member/find/pw")
    public void findPw(String email, ModelAttribute attrs){
        log.info("findPw(email= {})", email);
        Member member= memberService.findId(email);
    }

    @GetMapping("/member/find/resetPw")
    public void resetPw() {
        log.info("resetPw() GET");
    }

    @PostMapping("/member/find/resetPw")
    public String resetPw(ResetPasswordDto dto){
        log.info("resetPw(dto= {})", dto);
        memberService.resetPw(dto);
        return "redirect:/";
    }


}
