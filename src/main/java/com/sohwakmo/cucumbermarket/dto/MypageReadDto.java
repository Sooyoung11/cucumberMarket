package com.sohwakmo.cucumbermarket.dto;

import com.sohwakmo.cucumbermarket.domain.Member;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MypageReadDto {

    private Integer memberNo;
    private String name;
    private String nickname;
    private String password;
    private String address;
    private String phone;
    private String email;

    public static MypageReadDto fromEntity(Member m){
        return MypageReadDto.builder()
                .memberNo(m.getMemberNo())
                .name(m.getName())
                .nickname(m.getNickname())
                .password(m.getPassword())
                .address(m.getAddress())
                .phone(m.getPhone())
                .email(m.getEmail())
                .build();
    }
}
