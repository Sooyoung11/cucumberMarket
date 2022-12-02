package com.sohwakmo.cucumbermarket.dto;

import com.sohwakmo.cucumbermarket.domain.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MemberSecurityDto extends User {

    private String memberId;
    private String password;
    private String name;
    private String nickname;
    private String address;
    private Integer phone;
    private String email;
    private Integer grade;

//    @Override
    public MemberSecurityDto(String memberId, String password, String name,
                             String nickname, String address, Integer phone,
                             String email, Integer grade,
                             Collection<? extends GrantedAuthority> authorities) {
        super(memberId, password, authorities);

        this.memberId = memberId;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.grade = grade;
    }

    public static MemberSecurityDto fromEntity(Member m){
        List<GrantedAuthority> authorities= m.getRoles().stream()
                .map(x -> new SimpleGrantedAuthority(x.getRole()))
                .collect(Collectors.toList());
        MemberSecurityDto dto= new MemberSecurityDto(m.getMemberId(),
                m.getPassword(), m.getName(), m.getNickname(), m.getAddress(),
                m.getPhone(), m.getEmail(), m.getGrade(), authorities);

        return dto;
    }
}
