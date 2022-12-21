package com.sohwakmo.cucumbermarket.repository;

import com.sohwakmo.cucumbermarket.domain.Member;
import com.sohwakmo.cucumbermarket.dto.MemberRegisterDto;
import com.sohwakmo.cucumbermarket.dto.ResetPasswordDto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    // memberId 일치하는 사용자 검색.

//    @Query("select m from MEMBER m where m.memberId= :memberId")
//    Optional<Member> findByMemberId(@Param(value = "memberId") String memberId);

    @EntityGraph(attributePaths = "roles")
    Optional<Member> findByMemberId(String memberId);

    @EntityGraph(attributePaths = "roles")
    Optional<Member> findByNickname(String nickname);

    @EntityGraph(attributePaths = "roles")
    Optional<Member> findByEmail(String email);

    @EntityGraph(attributePaths = "roles")
    Member save(MemberRegisterDto dto);


}