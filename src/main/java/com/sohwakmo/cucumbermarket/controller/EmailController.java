package com.sohwakmo.cucumbermarket.controller;

import com.sohwakmo.cucumbermarket.service.EmailService;
import com.sohwakmo.cucumbermarket.service.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@RequiredArgsConstructor
@Controller
public class EmailController {

    @Autowired
    private final EmailService emailService;
    private final EmailServiceImpl emailServiceImpl;

    /*
    @PostMapping("/member/sendEmail")
    public String sendEmail(@RequestParam String email) throws Exception {
        log.info("sendEmail(email= {})", email);
        String code= emailService.sendEmail(email);
        return code;
    }*/

    @GetMapping("/member/sendEmail")
    @ResponseBody
    public String sendEmail(String email) throws Exception {
        log.info("sendEmail(email= {})", email);
        String code= emailService.sendEmail(email);
        log.info("sendEmail(code= {}", code);
        return code;
    }

    @GetMapping("/member/emailAuth")
    @ResponseBody
    public ResponseEntity<String> checkEmailKey(String authCode, String emailKey){
        log.info("checkEmailKey(authCode= {}, emailKey= {}",authCode, emailKey);

        String result= emailServiceImpl.checkEmailKey(authCode, emailKey);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/member/find/pw/sendLink")
    @ResponseBody
    public String sendLink(String email) throws Exception{
        log.info("sendLink(email= {})", email);
        String authKey= emailService.sendLink(email);
        log.info("sendLink(authKey= {})", authKey);
        return authKey;
    }
}
