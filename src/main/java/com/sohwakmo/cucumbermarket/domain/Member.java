package com.sohwakmo.cucumbermarket.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

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
    private String passWord;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false, unique = true)
    private String nickName;

    @NotNull
    @Column(nullable = false)
    private String address;

    @NotNull
    @Column(nullable = false)
    private Integer phone;

    @NotNull
    @Column(unique = true, nullable = false)
    private String email;

    private Integer grade;

}
