package com.sohwakmo.cucumbermarket.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    JavaMailSender emailSender;

    public static final String code= createKey();
    private int size;

    /*[회원가입] 인증코드 발송*/
    private MimeMessage createMessage(String to) throws Exception{
        MimeMessage mimeMessage= emailSender.createMimeMessage();

        mimeMessage.addRecipients(RecipientType.TO, to);
        mimeMessage.setSubject("[오이마켓] 회원가입 인증코드입니다.");

        String msg= "";
        msg += "<div style='margin-top:40px;'>";
        msg += "<p style='font-color:#198754; margin-bottom: 12px;'><strong>[CUCUMBER MARKET]<strong></p>";
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

    /*[비밀번호 찾기] 링크 발송*/
    //인증키 생성
    private String getKey(int size) {
        this.size = size;
        return getAuthCode();
    }

    //인증코드 난수 발생
    private String getAuthCode() {
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        int num = 0;
        while(buffer.length() < size) {
            num = random.nextInt(10);
            buffer.append(num);
        }
        return buffer.toString();
    }

    private MimeMessage createMessage2(String to) throws Exception{
        MimeMessage mimeMessage= emailSender.createMimeMessage();

        mimeMessage.addRecipients(RecipientType.TO, to);
        mimeMessage.setSubject("[오이마켓] 계정 비밀번호 재설정 링크입니다.");

        //6자리 난수 인증번호 생성
        String authKey = getKey(32);

        String msg= "";
        msg += "<div style='margin-top:40px;'>";
        msg += "<p style='font-color:#198754; margin-bottom: 12px;'><strong>[CUCUMBER MARKET]<strong></p>";
        msg += "<p>안녕하세요. 오이마켓입니다.</p>";
        msg += "<p>아래 링크를 클릭하시면 비밀번호 재설정 페이지로 이동합니다.</p>";
        msg += "<p><strong><a href='http://localhost:8889/member/find/resetPw?email=";
        msg += to;
        msg += "&authKey=";
        msg += authKey;
        msg += "' target='_blenk'> 비밀번호 재설정 </a></strong></p><br/>";
        msg += "<p>비밀번호 변경을 원하지 않거나 변경 요청을 하지 않으신 경우<br/>이 메시지를 무시하고 삭제하여 주십시오.</p><br/>";
        msg += "<p>감사합니다.</p>";
        msg += "</div>";
        mimeMessage.setText(msg, "UTF-8", "html");
        mimeMessage.setFrom(new InternetAddress("eunsuu7@gamil.com", "오이마켓"));

        return mimeMessage;
    }
    //인증메일 보내기
    @Override
    public String sendLink(String to) throws Exception{
        MimeMessage mimeMessage= createMessage2(to);
        try{
            emailSender.send(mimeMessage);
        }catch(MailException e){
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
        log.info("sendLink(to= {}, authKey= {})", to);
        return to;
    }


}
