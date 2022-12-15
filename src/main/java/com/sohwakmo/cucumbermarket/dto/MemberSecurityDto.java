package com.sohwakmo.cucumbermarket.dto;

import com.sohwakmo.cucumbermarket.domain.Member;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
public class MemberSecurityDto extends User {

    private Integer memberNo;
    private String memberId;
    private String password;
    private String name;
    private String nickname;
    private String address;
    private String phone;
    private String email;
    private Double grade;

    private String oauth;

//    @Override
    public MemberSecurityDto(Integer memberNo, String memberId, String password, String name,
                             String nickname, String address, String phone,
                             String email, Double grade, String oauth,
                             Collection<? extends GrantedAuthority> authorities) {
        super(memberId, password, authorities);

        this.memberId = memberId;
        this.password = password;
        this.memberNo = memberNo;
        this.name = name;
        this.nickname = nickname;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.grade = grade;
        this.oauth = oauth;
    }

    public static MemberSecurityDto fromEntity(Member m){
        List<GrantedAuthority> authorities= m.getRoles().stream()
                .map(x -> new SimpleGrantedAuthority(x.getRole()))
                .collect(Collectors.toList());
        MemberSecurityDto dto= new MemberSecurityDto(m.getMemberNo(), m.getMemberId(),
                m.getPassword(), m.getName(), m.getNickname(), m.getAddress(),
                m.getPhone(), m.getEmail(), m.getGrade(), m.getOauth(), authorities);

        return dto;
    }
}
