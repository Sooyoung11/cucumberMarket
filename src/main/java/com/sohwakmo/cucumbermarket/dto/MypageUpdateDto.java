package com.sohwakmo.cucumbermarket.dto;

import com.sohwakmo.cucumbermarket.domain.Member;
import lombok.Data;

@Data
public class MypageUpdateDto {

    private Integer memberNo;
    private String name;
    private String nickname;
    private String address;
    private String phone;
    private String email;

    public Member toEntity(){
        return Member.builder()
                .memberNo(memberNo)
                .name(name)
                .nickname(nickname)
                .address(address)
                .phone(phone)
                .email(email)
                .build();
    }
}
