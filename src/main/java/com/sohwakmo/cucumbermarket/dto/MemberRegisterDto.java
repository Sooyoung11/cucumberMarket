package com.sohwakmo.cucumbermarket.dto;

import com.sohwakmo.cucumbermarket.domain.Member;
import com.sohwakmo.cucumbermarket.domain.MemberRole;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MemberRegisterDto {

    private String memberId;
    private String password;
    private String name;
    private String nickname;
    private String address;
    private String phone;
    private String email;
    private String emailKey;
    private Integer emailAuth;

    public Member toEntity(){
        return Member.builder()
                .memberId(memberId)
                .password(password)
                .name(name)
                .nickname(nickname)
                .address(address)
                .phone(phone)
                .email(email)
                .emailKey(emailKey)
                .emailAuth(emailAuth)
                .build()
                .addRole(MemberRole.USER);
    }
}
