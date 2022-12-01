package com.sohwakmo.cucumbermarket.dto;

import com.sohwakmo.cucumbermarket.domain.Member;

public class ProfileImageReadDto {
    private Integer memberNo;
    private String userImgUrl;
    private String userImgName;

    public Member toEntity(){
        return Member.builder()
                .memberNo(memberNo)
                .userImgUrl(userImgUrl)
                .userImgName(userImgName)
                .build();
    }
}
