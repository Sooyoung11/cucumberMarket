package com.sohwakmo.cucumbermarket.repository;

import com.sohwakmo.cucumbermarket.domain.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    // memberId 일치하는 사용자 검색.

//    @Query("select m from MEMBER m where m.memberId= :memberId")
//    Optional<Member> findByMemberId(@Param(value = "memberId") String memberId);
    Optional<Member> findByMemberId(String memberId);

}