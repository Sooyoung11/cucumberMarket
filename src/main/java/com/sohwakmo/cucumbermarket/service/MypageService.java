package com.sohwakmo.cucumbermarket.service;

import com.sohwakmo.cucumbermarket.domain.Member;
import com.sohwakmo.cucumbermarket.dto.MypageUpdateDto;
import com.sohwakmo.cucumbermarket.repository.MypageRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Service
public class MypageService {

    private final MypageRepository mypageRepository;

    @Transactional(readOnly = true)
    public Member loadProfile(Integer memberNo) {
        log.info("loadProfile(memberNo={})", memberNo);
        Member entity = mypageRepository.findByMemberNo(memberNo);
        log.info("Member Return value Service: entity={}", entity);
        return entity;
    }

    @Transactional
    public Integer update(MypageUpdateDto dto) {
        log.info("update(dto={})", dto);
        Member entity = mypageRepository.findByMemberNo(dto.getMemberNo());

        entity.memberUpdate(dto.getName(), dto.getNickname(), dto.getAddress(), dto.getPhone(), dto.getEmail());
        return dto.getMemberNo();
    }
}