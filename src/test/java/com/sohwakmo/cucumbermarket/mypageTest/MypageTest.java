package com.sohwakmo.cucumbermarket.mypageTest;

import com.sohwakmo.cucumbermarket.domain.Member;
import com.sohwakmo.cucumbermarket.repository.MypageRepository;



//import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


//@Slf4j
@SpringBootTest
public class MypageTest {
    @Autowired
    private MypageRepository mypageRepository;

    @Test
    public void testFindAll(){
        Assertions.assertNotNull(mypageRepository);
        List<Member> list = mypageRepository.findAll();
        Assertions.assertTrue(list.size()>0);

        for(Member m : list){

//            log.info("{} | {} ", m.getMemberId(), m.getNickname());
        }
    }
}
