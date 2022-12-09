package com.sohwakmo.cucumbermarket.domain;

import com.sohwakmo.cucumbermarket.dto.ProfileImageReadDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@DynamicInsert
@SequenceGenerator(name = "MEMBERS_SEQ_GEN",sequenceName = "MEMBER_SEQ", allocationSize = 1)
public class Member {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "MEMBERS_SEQ_GEN")
    private Integer memberNo; // Primary Key

    @Column(nullable = false, unique = true)
    private String memberId; // 2자 이상

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phone;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false, name= "email_key")
    private String emailKey;

    @Column(name= "email_auth")
    private Integer emailAuth;

    @ColumnDefault("0")
    private Integer grade;

    @ColumnDefault("'/images/mypage/default.jpg'")
    private String userImgUrl;

    @ColumnDefault("'default.jpg'")
    private String userImgName;


    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<MemberRole> roles = new HashSet<>();
    public Member addRole(MemberRole role){
        roles.add(role);
        return this;
    }

    // 회원정보 수정 업데이트
    public Member memberUpdate( String name, String nickname, String password, String address, String phone, String email){
        this.name = name;
        this.nickname = nickname;
        this.password = password;
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
    public Member userImageUpdate(String userImgName, String userImgUrl){
        this.userImgName = userImgName;
        this.userImgUrl = userImgUrl;
        return this;
    }


}
