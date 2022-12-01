package com.sohwakmo.cucumbermarket.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@SequenceGenerator(name = "MEMBERS_SEQ_GEN",sequenceName = "MEMBERS_SEQ", allocationSize = 1)
public class Member {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "MEMBERS_SEQ_GEN")
    private Integer memberNo; // Primary Key

    @NotNull
    @Column(nullable = false, unique = true)
    @Size(min = 2, max = 20, message = "아이디는 2자이상 20자 이하입니다.")
    private String memberId; // 2자 이상

    @NotNull
    @Size(min = 8, max = 20, message = "비밀번호는 8자이상 20자 이하입니다.")
    @Column(nullable = false)
    private String password;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false, unique = true)
    private String nickname;

    @NotNull
    @Column(nullable = false)
    private String address;

    @NotNull
    @Column(nullable = false)
    private String phone;

    @NotNull
    @Column(unique = true, nullable = false)
    private String email;

    private Integer grade;

    private boolean deleted;
    private String userImgUrl;
    private String userImgName;
    
    //Spring security 사용시 적용 예정
    //private Set<MemberRole> roles = new HashSet<>();
    
    // 회원정보 수정 업데이트
    public Member memberUpdate( String name, String nickname, String address, String phone, String email){
        this.name = name;
        this.nickname = nickname;
        this.address = address;
        this.phone = phone;
        this.email = email;
        return this;
    }

    // 회원 등급 수정 업데이트
    public Member gradeUpdate(Integer grade){
        this.grade = grade;
        return this;
    }

    //회원 이미지 수정 업데이트
    public Member userImageUpdate(String userImgUrl){
        this.userImgUrl = userImgUrl;
        return this;
    }

}
