package com.sohwakmo.cucumbermarket.repository;

import com.sohwakmo.cucumbermarket.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MypageRepository extends JpaRepository<Member, Integer> {

    //select * from member where member_id = 'member_id';
    Member findByMemberId(String memberId);
    Member findByMemberNo(Integer memberNo);

}
