package com.sohwakmo.cucumbermarket.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    private Integer phone;

    @NotNull
    @Column(unique = true, nullable = false)
    private String email;

    private Integer grade;

}
