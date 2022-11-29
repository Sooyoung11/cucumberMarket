package com.sohwakmo.cucumbermarket.service;

import com.sohwakmo.cucumbermarket.domain.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PostService {
    public Page<Post> searchPost(String searchText, String searchText1, Pageable pageable){
        //TODO 검색어 명령작성
        return null;
    }
}
