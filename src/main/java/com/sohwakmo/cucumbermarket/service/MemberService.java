package com.sohwakmo.cucumbermarket.service;

import com.sohwakmo.cucumbermarket.domain.Member;
import com.sohwakmo.cucumbermarket.dto.MemberRegisterDto;
import com.sohwakmo.cucumbermarket.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public String checkMemberId(String memberId){
        log.info("checkMemberId(memberId= {})", memberId);

        Optional<Member> result= memberRepository.findByMemberId(memberId);
        if(result.isPresent()){
            return "memberIdNok";
        }else{
            return "memberIdOk";
        }
    }

    public String checkPassword(String password){
        log.info("checkPassword(password= {})", password);
        int result= password.length();
        if(8>result|| result>16){
            return "passwordNok";
        }else{
            return "passwordOk";
        }
    }

    public String checkPassword2(String password, String password2){
        log.info("checkPassword2(password= {}, password2= {})", password, password2);

        if(password.equals(password2)){
            return "password2Ok";
        }else{
            return "password2Nok";
        }
    }

    public String checkNickname(String nickname){
        log.info("checkNickname(nickname= {})", nickname);

        Optional<Member> result= memberRepository.findByNickname(nickname);
        if(result.isPresent()){
            return "nicknameNok";
        }else{
            return "nicknameOk";
        }
    }

    public String checkEmail(String email){
        log.info("checkEmail(email= {})", email);

        Optional<Member> result= memberRepository.findByEmail(email);
        if(result.isPresent()){
            return "emailNok";
        }else{
            return "emailOk";
        }
    }

    public Member registerMember(MemberRegisterDto dto){
        log.info("registerMember(dto= {})", dto);

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        log.info("setPassword(dto= {})", dto);
        Member entity = memberRepository.save(dto.toEntity());
        log.info("entity= {}", entity);

        return entity;
    }

    public Member findMemberByMemberNo(Integer memberNo) {

        return memberRepository.findById(memberNo).orElse(null);
    }
}
