package com.sohwakmo.cucumbermarket.domain;


import jakarta.persistence.*;
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
    private Integer phone;


    @Column(unique = true, nullable = false)
    private String email;

    private Integer grade;

}
