package com.sohwakmo.cucumbermarket.dto;

import com.sohwakmo.cucumbermarket.domain.Member;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MypageUpdateDto {

    private Integer memberNo;
    private String memberId;
    private String name;
    private String nickname;
    private String password;
    private String address;
    private String phone;
    private String email;


}
