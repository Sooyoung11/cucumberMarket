package com.sohwakmo.cucumbermarket.service;

import com.sohwakmo.cucumbermarket.domain.Member;
import com.sohwakmo.cucumbermarket.dto.MemberRegisterDto;
import com.sohwakmo.cucumbermarket.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public String checkMemberId(String memberId){
        log.info("checkMemberId(memberId= {})", memberId);

        Optional<Member> result= memberRepository.findByMemberId(memberId);
        if(result.isPresent()){
            return "nok";
        }else{
            return "ok";
        }
    }

    public Member registerMember(MemberRegisterDto dto){
        log.info("registerMember(dto= {})", dto);

        Member entity = memberRepository.save(dto.toEntity());
        log.info("entity= {}", entity);

        return entity;
    }
}
