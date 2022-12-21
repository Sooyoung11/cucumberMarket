package com.sohwakmo.cucumbermarket.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sohwakmo.cucumbermarket.domain.KakaoProfile;
import com.sohwakmo.cucumbermarket.domain.Member;
import com.sohwakmo.cucumbermarket.domain.OAuthToken;
import com.sohwakmo.cucumbermarket.dto.MemberRegisterDto;
import com.sohwakmo.cucumbermarket.dto.ResetPasswordDto;
import com.sohwakmo.cucumbermarket.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    @Value("${cos.key}")
    private String cosKey;

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public String checkMemberId(String memberId){
        log.info("checkMemberId(memberId= {})", memberId);

        Optional<Member> result= memberRepository.findByMemberId(memberId);
        if(result.isPresent()){
            return "memberIdNok";
        }else{
            return "memberIdOk";
        }
    }

    @Transactional(readOnly = true)
    public String checkPassword(String password){
        log.info("checkPassword(password= {})", password);
        int result= password.length();
        if(8>result|| result>16){
            return "passwordNok";
        }else{
            return "passwordOk";
        }
    }

    @Transactional(readOnly = true)
    public String checkPassword2(String password, String password2){
        log.info("checkPassword2(password= {}, password2= {})", password, password2);

        if(password.equals(password2)){
            return "password2Ok";
        }else{
            return "password2Nok";
        }
    }

    @Transactional(readOnly = true)
    public String checkNickname(String nickname){
        log.info("checkNickname(nickname= {})", nickname);

        Optional<Member> result= memberRepository.findByNickname(nickname);
        if(result.isPresent()){
            return "nicknameNok";
        }else{
            return "nicknameOk";
        }
    }

    @Transactional(readOnly = true)
    public String checkEmail(String email){
        log.info("checkEmail(email= {})", email);

        Optional<Member> result= memberRepository.findByEmail(email);
        if(result.isPresent()){
            return "emailNok";
        }else{
            return "emailOk";
        }
    }

    @Transactional
    public Member registerMember(MemberRegisterDto dto){
        log.info("registerMember(dto= {})", dto);

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        log.info("setPassword(dto= {})", dto);
        Member entity = memberRepository.save(dto.toEntity());
        log.info("entity= {}", entity);

        return entity;
    }

    @Transactional
    public Member findId(String email){
        log.info("findId(email= {})", email);
        return memberRepository.findByEmail(email).orElse(null);
    }

    @Transactional(readOnly = true)
    public Member findMemberByMemberNo(Integer memberNo) {
        return memberRepository.findById(memberNo).orElse(null);
    }

    @Transactional(readOnly = true)
    public Member findRegisterMember(String memberId) {
        Member member = memberRepository.findByMemberId(memberId).orElseGet(()-> {
            return new Member();
        });
        return member;
    }

    @Transactional
    public void resetPw(ResetPasswordDto dto){
        log.info("resetPw(dto= {})", dto);
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        Member entity= memberRepository.findByEmail(dto.getEmail()).orElse(null);
        log.info(entity.toString());
        Member member = entity.resetPassword(dto.getPassword(), dto.getEmailKey());
        log.info(member.toString());
    }

    @Transactional
    public MemberRegisterDto socialLogin(String code){

        RestTemplate rt = new RestTemplate();
        //HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        ObjectMapper objectMapper = new ObjectMapper();

        //---------------Post: 토큰 발급 요청 start-----------------------------
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        //HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "17f10af702d544834112ae55dc49d845");
        params.add("redirect_uri", "http://192.168.20.28:8889/auth/kakao/callback");
        params.add("code", code);

        //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        //Http 요청하기-Post방식으로 -그리고 response 변수의 응답 받음
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );
        //---------------Post: 토큰 발급 요청 end ------------------------------

        //---------------Post: 토큰 발급 응답 read Zone------------------------------
        OAuthToken oauthToken = null;
        try {
            oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonMappingException e){
            e.printStackTrace();
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }

        log.info("카카오 액세스 토큰: "+ oauthToken.getAccess_token());
        //---------------Post: 토큰 발급 응답 read Zone------------------------------

        //---------------POST: 토큰을 통한 사용자 정보 조회 start-----------------------
        headers.add("Authorization", "Bearer "+ oauthToken.getAccess_token());
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

        //Http 요청하기-Post방식으로 -그리고 response 변수의 응답 받음
        ResponseEntity<String> response2 = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonMappingException e){
            e.printStackTrace();
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
//
//        log.info("카카오 아이디(번호): "+kakaoProfile.getId());
//        log.info("카카오 이메일: "+kakaoProfile.getKakao_account().getEmail());
//
//        log.info("마켓 사용자 아이디:"+kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId());

        int idx = kakaoProfile.getKakao_account().getEmail().indexOf("@");
        String kakao_nickname = kakaoProfile.getKakao_account().getEmail().substring(0, idx) +"_"+kakaoProfile.getId().toString().substring(0,4);
//        log.info("kakao_memberId="+kakao_memberId);
//        log.info("kakao_nickname="+kakao_nickname);
//        log.info("마켓 서버 이메일: "+ kakaoProfile.getKakao_account().getEmail());
//        log.info("마켓 서버 닉네임: "+ kakaoProfile.getProperties().getNickname());
//        log.info("마켓 서버 이름: "+ "kakaoUser" + kakaoProfile.getId());
//        log.info("사용자 프로필 사진: "+ kakaoProfile.getKakao_account().getProfile().getProfile_image_url());
//        log.info("마켓 서버 패스워드: "+ cosKey);
        //---------------POST: 토큰을 통한 사용자 정보 조회 end-----------------------

        //---------------불러온 사용자 정보를 MeberRegisterDto에 저장-------------------
        MemberRegisterDto kakaoMember = MemberRegisterDto.builder()
                .memberId(kakao_nickname)
                .password(cosKey)
                .nickname(kakao_nickname)
                .name(kakaoProfile.getProperties().getNickname())
                .email(kakaoProfile.getKakao_account().getEmail())
                .oauth("kakao")
                .userImgName("kakaoImage"+ kakaoProfile.getId())
                .userImgUrl(kakaoProfile.getKakao_account().getProfile().getProfile_image_url())
                .build();

        return kakaoMember;

    }
}
