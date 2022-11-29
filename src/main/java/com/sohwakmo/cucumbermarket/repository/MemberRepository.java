package com.sohwakmo.cucumbermarket.repository;

import com.sohwakmo.cucumbermarket.domain.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    // memberId 일치하는 사용자 검색.
    @EntityGraph(attributePaths = "roles")
    Optional<Member> findByMemberId(String MemberId);
}
