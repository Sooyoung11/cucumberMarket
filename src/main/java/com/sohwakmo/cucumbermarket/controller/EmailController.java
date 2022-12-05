package com.sohwakmo.cucumbermarket.controller;

import com.sohwakmo.cucumbermarket.service.EmailService;
import com.sohwakmo.cucumbermarket.service.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@RequiredArgsConstructor
@Controller
public class EmailController {

    @Autowired
    private final EmailService emailService;

    @PostMapping("/member/check_emailkey")
    public void emailConfirm(String email) throws Exception {
        log.info("emailConfirm(email= {}) POST", email);
        emailService.sendEmail(email);
    }

    @PostMapping("/member/verifyCode")
    @ResponseBody
    public int verifyCode(String code){
        log.info("verifyCode(code= {} POST", code);
        int result= 0;
        if(EmailServiceImpl.emailKey.equals(code)){
            result= 1;
        }
        return result;
    }
}
