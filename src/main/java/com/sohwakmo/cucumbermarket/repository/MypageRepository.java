package com.sohwakmo.cucumbermarket.repository;

import com.sohwakmo.cucumbermarket.domain.Member;
import com.sohwakmo.cucumbermarket.dto.ProfileImageReadDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MypageRepository extends JpaRepository<Member, Integer> {

    //select * from member where member_id = 'member_id';
    Member findByMemberNo(Integer memberNo);
    //ProfileImageReadDto findByMemberNo(Integer memberNo);


}
