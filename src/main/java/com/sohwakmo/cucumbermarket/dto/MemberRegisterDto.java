package com.sohwakmo.cucumbermarket.dto;

import com.sohwakmo.cucumbermarket.domain.Member;
import com.sohwakmo.cucumbermarket.domain.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
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

    private String oauth;

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
                .oauth(oauth)
                .build()
                .addRole(MemberRole.USER);
    }
}
