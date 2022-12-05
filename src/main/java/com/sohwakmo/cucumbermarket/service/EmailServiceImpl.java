package com.sohwakmo.cucumbermarket.service;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    JavaMailSender emailSender;

    public static final String emailKey= createKey();

    private MimeMessage createMessage(String to) throws Exception{
        log.info("createMessage(to= {}, mailKey= {}", to, emailKey);
        MimeMessage mimeMessage= emailSender.createMimeMessage();

        mimeMessage.addRecipients(Message.RecipientType.TO, to);
        mimeMessage.setSubject("[오이마켓] 회원가입 인증코드입니다.");

        String msg= "";
        msg += "<link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">";
        msg += "<link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>";
        msg += "<link href=\"https://fonts.googleapis.com/css2?family=Dokdo&display=swap\" rel=\"stylesheet\">";
        msg += "<div style='margin-top:100px;'>";
        msg += "<div style='font-family:\"Dokdo\";font-size:64px;color:green;line-height:.6em;text-align:center;margin:40px;'>";
        msg += "cucumber<br>market</div>";
        msg += "<p>안녕하세요. 오이마켓입니다.</p>";
        msg += "<p>아래 코드를 회원가입 페이지로 돌아가 입력해주세요.</p>";
        msg += "<p>인증코드: <strong>"+emailKey+"</strong></p><br/>";
        msg += "<p>갑사합니다.</p>";
        msg += "</div>";
        mimeMessage.setText(msg, "UTF-8", "html");
        mimeMessage.setFrom(new InternetAddress("eunsuu7@gamil.com", "오이마켓"));

        return mimeMessage;
    }

    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    //  A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }
        return key.toString();
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
        return emailKey;
    }
}
