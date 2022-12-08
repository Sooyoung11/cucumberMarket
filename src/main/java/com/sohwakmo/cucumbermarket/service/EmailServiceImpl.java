package com.sohwakmo.cucumbermarket.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    JavaMailSender emailSender;

    public static final String code= createKey();

    private MimeMessage createMessage(String to) throws Exception{
        MimeMessage mimeMessage= emailSender.createMimeMessage();

        mimeMessage.addRecipients(RecipientType.TO, to);
        mimeMessage.setSubject("[오이마켓] 회원가입 인증코드입니다.");

        String msg= "";
        msg += "<div style='margin-top:40px;'>";
        msg += "<p style='font-color:#008000; margin-bottom: 12px;'><strong>[CUCUMBER MARKET]<strong></p>";
        msg += "<p>안녕하세요. 오이마켓입니다.</p>";
        msg += "<p>아래 코드를 회원가입 페이지로 돌아가 입력해주세요.</p>";
        msg += "<p>인증코드: <strong>"+code+"</strong></p><br/>";
        msg += "<p>감사합니다.</p>";
        msg += "</div>";
        mimeMessage.setText(msg, "UTF-8", "html");
        mimeMessage.setFrom(new InternetAddress("eunsuu7@gamil.com", "오이마켓"));

        return mimeMessage;
    }

    public static String createKey() {
        StringBuffer stringBuffer = new StringBuffer();
        Random random = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = random.nextInt(3); // 0~2 까지 랜덤

            switch (index) {
                case 0:
                    stringBuffer.append((char) ((int) (random.nextInt(26)) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    stringBuffer.append((char) ((int) (random.nextInt(26)) + 65));
                    //  A~Z
                    break;
                case 2:
                    stringBuffer.append((random.nextInt(10)));
                    // 0~9
                    break;
            }
        }
        return stringBuffer.toString();
    }

   @Override
    public String sendEmail(String to) throws Exception{
        MimeMessage mimeMessage= createMessage(to);
        try{
            emailSender.send(mimeMessage);
        }catch(MailException e){
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
        log.info("sendEmail(to= {}, code= {})", to, code);
        return code;
    }

    public String checkEmailKey(String authCode, String emailKey){
        log.info("checkPassword2(password= {}, password2= {})", authCode, emailKey);

        if(authCode.equals(emailKey)){
            return "emailKeyOk";
        }else{
            return "emailKeyNok";
        }
    }
}
