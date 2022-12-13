package com.sohwakmo.cucumbermarket.repository;

import com.sohwakmo.cucumbermarket.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Integer> {
    List<Message> findByRoomIdAndMessageNumOrderById(String roomId, Integer memberNo);

    List<Message> findByMessageNumAndRoomIdOrderByIdDesc(Integer memberNo,String roomId);
}
